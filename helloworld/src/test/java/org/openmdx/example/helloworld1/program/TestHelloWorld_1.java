/*
 * ====================================================================
 * Project:     openMDX/Example, http://www.openmdx.org/
 * Description: HelloWorld client
 * Owner:       the original authors.
 * ====================================================================
 *
 * This software is published under the BSD license as listed below.
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
import javax.naming.spi.NamingManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmdx.base.exception.ServiceException;
import org.openmdx.base.jmi1.Authority;
import org.openmdx.base.jmi1.Provider;
import org.openmdx.example.helloworld1.jmi1.HelloWorld;
import org.openmdx.example.helloworld1.jmi1.HelloWorldSayHelloParams;
import org.openmdx.example.helloworld1.jmi1.Helloworld1Package;
import org.openmdx.example.helloworld1.jmi1.SayHelloResult;
import org.openmdx.kernel.lightweight.naming.NonManagedInitialContextFactoryBuilder;
import org.openmdx.kernel.log.SysLog;

import junit.framework.TestCase;

/**
 * HelloWorld JUnit
 */
public class TestHelloWorld_1 extends TestCase {

	/**
	 * Retrieve the Hello World provider name
	 * 
	 * @return The Hello World provider name
	 */
	protected String getProviderName(
	){
		return "JUnit";
	}

	@BeforeEach
	public synchronized void initialize(
	) throws Exception {  
		System.out.println(">>>> **** Test Start: " + getName());
		SysLog.info("Test Start", getName());
		if(!NamingManager.hasInitialContextFactoryBuilder()) {
			NonManagedInitialContextFactoryBuilder.install(null);
		}
        this.pm = JDOHelper.getPersistenceManagerFactory("EntityManagerFactory").getPersistenceManager(
        	 "guest",
        	 null
        );
    }

	@AfterEach
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
	@Test
	public void testHelloWorld() throws Exception{
        try {
    		helloWorld("Standard");
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
		String segmentName
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
            segmentName, 
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
