/*
 * ====================================================================
 * Project:     openMDX/Example, http://www.openmdx.org/
 * Name:        $Id: TestHelloWorld_1.java,v 1.39 2011/02/09 20:55:42 hburger Exp $
 * Description: HelloWorld client
 * Revision:    $Revision: 1.39 $
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * Date:        $Date: 2011/02/09 20:55:42 $
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
 * 
 * Copyright (c) 2004-2008, OMEX AG, Switzerland
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 * 
 * * Neither the name of the openMDX team nor the names of its
 *   contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 * ------------------
 * 
 * This product includes software developed by other organizations as
 * listed in the NOTICE file.
 */
package org.openmdx.example.helloworld1.program;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.openmdx.base.exception.ServiceException;
import org.openmdx.base.jmi1.Authority;
import org.openmdx.base.jmi1.Provider;
import org.openmdx.example.helloworld1.jmi1.HelloWorld;
import org.openmdx.example.helloworld1.jmi1.HelloWorldSayHelloParams;
import org.openmdx.example.helloworld1.jmi1.Helloworld1Package;
import org.openmdx.example.helloworld1.jmi1.SayHelloResult;
import org.openmdx.kernel.lightweight.naming.NonManagedInitialContextFactoryBuilder;
import org.openmdx.kernel.log.SysLog;

/**
 * HelloWorld client
 */
public class TestHelloWorld_1 
    extends TestCase {

	/**
	 * Constructor
	 * 
	 * @param name
	 * @throws NamingException 
	 */
	public TestHelloWorld_1(
		String name
	) throws NamingException{
		super(name);
		if(!NamingManager.hasInitialContextFactoryBuilder()) {
			NonManagedInitialContextFactoryBuilder.install(null);
		}
	}  

	/**
	 * Launch the test suite from command line
	 * 
	 * @param args
	 */
	public static void main(
		String[] args
	){
		TestRunner.run(suite());
	}

	/**
	 * Retrieve the test suite
	 * 
	 * @return this class' test suite
	 */
	public static Test suite(
	){
		return new TestSuite(TestHelloWorld_1.class);
	}

	/**
	 * Retrieve the Hello World provider name
	 * 
	 * @return The Hello World provider name
	 */
	protected String getProviderName(
	){
		return "JUnit";
	}

	/**
	 * Retrieve the Hello World segment name
	 * 
	 * @return the Hello World segment name
	 * 
	 */
	protected String getSegmentName(){
		return getName().substring("test".length());
	}

	/**
	 * Set up the test fixture
	 */
	@Override
	protected synchronized void setUp(
	) throws Exception {  
		System.out.println(">>>> **** Test Start: " + getName());
		SysLog.info("Test Start", getName());		
        this.pm = JDOHelper.getPersistenceManagerFactory("EntityManagerFactory").getPersistenceManager(
        	 "guest",
        	 null
        );
    }

	/**
	 * Tear down the test fixture
	 */
	@Override
	protected void tearDown(
	) throws ServiceException {
		SysLog.info("Test End", getName());
		try {
			this.pm.close();
		} catch(Exception e) {}
		System.out.println("<<<< **** Test End: " + getName());
	}

	/**
	 * Test with<ul>
	 * <li>In-process deployment of lightweight container
	 * <li>Standard application plugin
	 * <li>In-memory persistence plugin
	 * </ul>
	 * 
	 * @throws Exception test aborted with error
	 */
	public void testHelloWorld() throws Exception{
        try {
    		helloWorld();
        } catch(Exception exception) {
        	SysLog.error("Exception", exception);
            throw exception;
        }
	}

	/**
	 * Run the hello world test
	 * 
	 * @throws Exception
	 */
	protected void helloWorld(
    ) throws Exception {
        System.out.println("Retrieving Helloworld1 authority...");
        Authority authority = this.pm.getObjectById(
            Authority.class,
            Helloworld1Package.AUTHORITY_XRI
        );
        System.out.println("Getting helloworld1 provider...");
        Provider provider = authority.getProvider(getProviderName());
        System.out.println("Creating helloworld1 segment...");
        this.pm.currentTransaction().begin();
        HelloWorld helloWorld = this.pm.newInstance(
    		HelloWorld.class
        );
//      helloWorld.setHitCount(0);
        provider.addSegment(
            false,
            getSegmentName(), 
            helloWorld
        );
        this.pm.currentTransaction().commit();
        
        //
        // sayHello on this instance. The sayHello operation of HelloWorldImpl is called
        //
        System.out.println("Retrieving Helloworld1 package...");
        Helloworld1Package helloWorldPkg = (Helloworld1Package) helloWorld.refImmediatePackage();
        SayHelloResult hResult = null;
        HelloWorldSayHelloParams sayHelloParams = helloWorldPkg.createHelloWorldSayHelloParams("de");
        hResult = helloWorld.sayHello(sayHelloParams);
        System.out.println("Client: sayHello[de]=" + hResult.getMessage());
        sayHelloParams = helloWorldPkg.createHelloWorldSayHelloParams("fr");
        hResult = helloWorld.sayHello(sayHelloParams);
        System.out.println("Client: sayHello[fr]=" + hResult.getMessage());
        sayHelloParams = helloWorldPkg.createHelloWorldSayHelloParams("en");
        hResult = helloWorld.sayHello(sayHelloParams);
        System.out.println("Client: sayHello[en]=" + hResult.getMessage());
        //
        // sayGoodBye on this instance. The sayHello operation of HelloWorldImpl is called
        //
//        SayGoodByeResult gResult = null;        
//        HelloWorldSayGoodByeParams sayGoodByeParams = helloWorldPkg.createHelloWorldSayGoodByeParams("de");
//        gResult = helloWorld.sayGoodBye(sayGoodByeParams);
//        System.out.println("Client: sayGoodBye[de]=" + gResult.getMessage());
//        sayGoodByeParams = helloWorldPkg.createHelloWorldSayGoodByeParams("fr");
//        gResult = helloWorld.sayGoodBye(sayGoodByeParams);
//        System.out.println("Client: sayGoodBye[fr]=" + gResult.getMessage());
//        sayGoodByeParams = helloWorldPkg.createHelloWorldSayGoodByeParams("en");
//        gResult = helloWorld.sayGoodBye(sayGoodByeParams);
//        System.out.println("Client: sayGoodBye[en]=" + gResult.getMessage());
        //
        // Retrieve hit count
        //
//        helloWorld.refRefresh();
//        System.out.println("Client: hitCount=" + helloWorld.getHitCount());
	}
	
	//-----------------------------------------------------------------------
	// Members	
	//-----------------------------------------------------------------------
	protected PersistenceManager pm;
		
}
