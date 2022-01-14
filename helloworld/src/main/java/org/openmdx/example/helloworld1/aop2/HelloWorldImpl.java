/*
 * ====================================================================
 * Project:     openMDX/Helloworld, http://www.openmdx.org/
 * Description: HelloWorld implementation
 * Owner:       the original authors.
 * ====================================================================
 *
 * This software is published under the BSD license
 * as listed below.
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
package org.openmdx.example.helloworld1.aop2;

import javax.jmi.reflect.RefException;

import org.openmdx.base.aop2.AbstractObject;
import org.openmdx.example.helloworld1.jmi1.HelloWorldSayHelloParams;
import org.openmdx.example.helloworld1.jmi1.Helloworld1Package;
import org.openmdx.example.helloworld1.jmi1.SayHelloResult;

public class HelloWorldImpl
	<S extends org.openmdx.example.helloworld1.jmi1.HelloWorld,N extends org.openmdx.example.helloworld1.cci2.HelloWorld,C extends Void>
	extends AbstractObject<S,N,C> {

	//---------------------------------------------------------------------------
    public HelloWorldImpl(
        S same,
        N next
    ) {
    	super(same, next);
        System.out.println("Plugin: instantiating HelloWorldImpl");
    }
              
    //---------------------------------------------------------------------------
    public SayHelloResult sayHello(
	    HelloWorldSayHelloParams params
    ) throws RefException {
        System.out.println("Plugin: invoking sayHello(language=" + params.getLanguage() + ")");
        String language = params.getLanguage();
        String message = null;
        if("de".equals(language)) {
            message = "hallo welt";
        }
        else if("fr".equals(language)) {
            message = "bonjour monde";
        }
        else {
            message = "hello world";
        }
       
        // increment hitCount. 
//        this.next.setHitCount(this.next.getHitCount() + 1);
        
        return ((Helloworld1Package)this.sameObject().refOutermostPackage().refPackage(
            Helloworld1Package.class.getName())
        ).createSayHelloResult(message);
    }

    //---------------------------------------------------------------------------
//    public SayGoodByeResult sayGoodBye(
//        HelloWorldSayGoodByeParams params
//    ) throws RefException {
//        System.out.println("Plugin: invoking sayGoodBye(language=" + params.getLanguage() + ")");
//        String language = params.getLanguage();
//        String message = null;
//        if("de".equals(language)) {
//            message = "auf wiedersehen welt";
//        }
//        else if("fr".equals(language)) {
//            message = "adieu monde";
//        }
//        else {
//            message = "good bye world";
//        }
//
//        // increment hitCount. 
//        this.next.setHitCount(this.next.getHitCount() + 1);
//
//        return ((Helloworld1Package)this.current.refOutermostPackage().refPackage(
//            Helloworld1Package.class.getName())
//        ).createSayGoodByeResult(message);
//    }

    //-----------------------------------------------------------------------
    // Members
    //-----------------------------------------------------------------------
    
}

//--- End of File -----------------------------------------------------------
