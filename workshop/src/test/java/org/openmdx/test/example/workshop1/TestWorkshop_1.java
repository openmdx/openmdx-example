/*
 * ====================================================================
 * Project:     openMDX/Workshop, http://www.openmdx.org/
 * Description: openMDX/Example Workshop 
 * Owner:       OMEX AG, Switzerland, http://www.omex.ch
 * ====================================================================
 *
 * This software is published under the BSD license
 * as listed below.
 * 
 * Copyright (c) 2006-2012, OMEX AG, Switzerland
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
package org.openmdx.test.example.workshop1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.naming.spi.NamingManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmdx.base.exception.ServiceException;
import org.openmdx.base.jmi1.Authority;
import org.openmdx.base.jmi1.Provider;
import org.openmdx.base.naming.Path;
import org.openmdx.example.workshop1.cci2.ProjectQuery;
import org.openmdx.example.workshop1.cci2.SkillQuery;
import org.openmdx.example.workshop1.cci2.TeamMemberQuery;
import org.openmdx.example.workshop1.jmi1.AssignTeamMemberParams;
import org.openmdx.example.workshop1.jmi1.AssignTeamMemberResult;
import org.openmdx.example.workshop1.jmi1.Project;
import org.openmdx.example.workshop1.jmi1.Segment;
import org.openmdx.example.workshop1.jmi1.Skill;
import org.openmdx.example.workshop1.jmi1.Task;
import org.openmdx.example.workshop1.jmi1.TeamMember;
import org.openmdx.example.workshop1.jmi1.Workshop1Package;
import org.openmdx.kernel.exception.BasicException;
import org.openmdx.kernel.lightweight.naming.NonManagedInitialContextFactoryBuilder;
import org.openmdx.kernel.log.SysLog;

import junit.framework.TestCase;

/**
 * openMDX/Example workshop1 JUnit
 */
public class TestWorkshop_1 extends TestCase {

	@BeforeEach
	public void initialize(
	) throws Exception {
		System.out.println(">>>> **** Start: " + getName());
		SysLog.info("Start", getName());
        System.out.println("Aquire persistence manager...");      
        SysLog.info("Aquire persistence manager...");
		if(!NamingManager.hasInitialContextFactoryBuilder()) {
			NonManagedInitialContextFactoryBuilder.install(
				Collections.singletonMap(
					"org.openmdx.comp.env.jdbc.jdbc_openmdx_example_workshop",
					"jdbc:hsqldb:hsql:\\/\\/127.0.0.1:9002\\/WORKSHOP?user=sa&password=manager99&driver=org.hsqldb.jdbcDriver"
				)
			);
		}
        this.pm = JDOHelper.getPersistenceManagerFactory("EntityManagerFactory").getPersistenceManager(
        	"junit",
        	null
       );
    }

	/**
	 * Retrieve the provider's path
	 * 
	 * @return The provider's path
	 */
	protected String getProviderName(
	){
		return "Workshop";
	}

	@Test
	public void testStandard() throws Exception{
		try {
            example1("Standard", true);
	    } catch(Exception exception) {
	    	SysLog.error("Exception", exception);
	        throw exception;
	    }
	}

	@Test
    public void testVolatile() throws Exception{
        try {
            example1("Volatile", false);
        } catch(Exception exception) {
        	SysLog.error("Exception", exception);
            throw exception;
        }
    }

	@AfterEach
	protected void tearDown(
	) throws ServiceException {
        System.out.println("Closing peristence manager factory...");
		SysLog.info("End", getName());
		System.out.println("<<<< **** End: " + getName());
        try {
            this.pm.close();
        } catch(Exception e) {}
	}

	/**
	 * Clear and Populate
	 * 
	 * @throws Exception
	 */
	protected void example1(
		String segmentName,
		boolean supportsView
    ) throws Exception {
        Transaction unitOfWork = pm.currentTransaction();
        Authority authority = this.pm.getObjectById(
            Authority.class,
            Workshop1Package.AUTHORITY_XRI
        );        
		//
		// Reset workshop1 segment
		//
        Provider provider = authority.getProvider(getProviderName());
        System.out.println("Creating example1 segment...");
        Segment segment = (Segment) provider.getSegment(segmentName);
        if(segment != null) {
        	unitOfWork.begin();
        	segment.refDelete();
        	unitOfWork.commit();
        }
        unitOfWork.begin();
        segment = this.pm.newInstance(
            Segment.class
        );
        provider.addSegment(
            false,
            segmentName, 
            segment
        );
        unitOfWork.commit();
        
        System.out.println("Retrieving Workshop1 package...");        
        Workshop1Package pkg = (Workshop1Package)segment.refImmediatePackage();
        
        //
        // Populate example1 segment
        //
        Project workshop;
        Task openmdxTrack;
        Task opencrxTrack;
        Skill mdaKnowledge;
        Skill j2eeKnowledge;
        Skill openmdxKnowledge;
        Skill opencrxKnowledge;
        Skill pascalProgramming;
        Skill lispProgramming;
        TeamMember john;
        TeamMember jane;
        unitOfWork.begin();
        mdaKnowledge = pkg.getSkill().createSkill();
        mdaKnowledge.setName("MDA knowledge");
        segment.addSkill(
            false,
        	"MDA",
        	mdaKnowledge
        );
        j2eeKnowledge = pkg.getSkill().createSkill();
        j2eeKnowledge.setName("Jave 2 Enterprise Edition knowledge");
        segment.addSkill(
            false,
        	"J2EE",
        	j2eeKnowledge
        );
        openmdxKnowledge = pkg.getSkill().createSkill();
        openmdxKnowledge.setName("openMDX knowledge");
        segment.addSkill(
            false,
        	"openMDX",
        	openmdxKnowledge
        );
        opencrxKnowledge = pkg.getSkill().createSkill();
        opencrxKnowledge.setName("openCRX knowledge");
        segment.addSkill(
            false,
        	"openCRX",
        	opencrxKnowledge
        );
        pascalProgramming = pkg.getSkill().createSkill();
        pascalProgramming.setName("Pascal programming");
        segment.addSkill(
            false,
        	"Pascal",
        	pascalProgramming
        );
        lispProgramming = pkg.getSkill().createSkill();
        lispProgramming.setName("Lisp programming");
        segment.addSkill(
            false,
        	"Lisp",
        	lispProgramming
        );
        Collection<Skill> basicSkills = Arrays.asList(
        	new Skill[]{mdaKnowledge, j2eeKnowledge}
        );
        john = pkg.getTeamMember().createTeamMember();
        john.setName("John Dow");
        segment.addTeamMember(john);
        {
	        Collection<Skill> skills = john.getSkill();
	        skills.addAll(basicSkills);
	        skills.add(openmdxKnowledge);
	        skills.add(pascalProgramming);
        }
        jane = pkg.getTeamMember().createTeamMember();
        jane.setName("Jane Dow");
        segment.addTeamMember(jane);
        {
	        Collection<Skill> skills = jane.getSkill();
	        skills.addAll(basicSkills);
	        skills.add(opencrxKnowledge);
	        skills.add(lispProgramming);
        }
        workshop = pkg.getProject().createProject();
        workshop.setName("Workshop");
        workshop.setDescription("openMDX/openCRX Workshop (2006-02-27 to 2006-03-01");
        segment.addProject(
            false,
    		"SICLE",
    		workshop
        );
        openmdxTrack = pkg.getTask().createTask();
        openmdxTrack.setName("openMDX Track");
        workshop.addTask(
            false,
    		"openMDX",
    		openmdxTrack
        );
        {
        	Collection<Skill> skills = openmdxTrack.getSkill();
	        skills.addAll(basicSkills);
	        skills.add(openmdxKnowledge);
        }
        opencrxTrack = pkg.getTask().createTask();
        opencrxTrack.setName("openCRX Track");
        workshop.addTask(
            false,
    		"openCRX",
    		opencrxTrack
        );
        {
	        Collection<Skill> skills = opencrxTrack.getSkill();
	        skills.addAll(basicSkills);
	        skills.add(opencrxKnowledge);
        }
        unitOfWork.commit();
        //
        // Assign team member
        //
        unitOfWork.begin();
        AssignTeamMemberParams assignTeamMemberParams = pkg.createAssignTeamMemberParams("participant", john);
        AssignTeamMemberResult johnsResult = openmdxTrack.assignTeamMember(assignTeamMemberParams);
        unitOfWork.commit();
        unitOfWork.begin();
        assignTeamMemberParams = pkg.createAssignTeamMemberParams("listener", jane);
        AssignTeamMemberResult janesResult = opencrxTrack.assignTeamMember(assignTeamMemberParams);
        unitOfWork.commit();
        assertEquals("John's role", "participant", johnsResult.getAssignment().getMemberRole());
        assertEquals("Jane's role", "listener", janesResult.getAssignment().getMemberRole());
        //
        // Retrieve assignment
        //
        if(supportsView) {
            Collection<Task> tasks = john.getTask(); 
            for(Task t: tasks) {
            	assertTrue(
            		"Task identity " + t.getIdentity(), 
            		new Path(t.getIdentity()).isLike(TASK_IDENTITY)
            	);
            }
            System.out.println("Jane's tasks:");
            tasks = jane.getTask();
            for(Task t: tasks) {
            	assertTrue(
            		"Task identity " + t.getIdentity(), 
            		new Path(t.getIdentity()).isLike(TASK_IDENTITY)
            	);
            }
        }
        //
        // Validation Test
        //
        try {
            unitOfWork.begin();
            assignTeamMemberParams = pkg.createAssignTeamMemberParams("observer", john);
            johnsResult = opencrxTrack.assignTeamMember(assignTeamMemberParams);
	        unitOfWork.commit();
	        fail("Missing skills");
        } 
        catch (Exception exception) {
        	BasicException cause = BasicException.toExceptionStack(exception).getCause("Workshop"); 
        	assertEquals(
        		"MissingSkillsException code", 
        		BasicException.Code.BAD_PARAMETER,
        		cause.getExceptionCode()
        	);
        	assertEquals(
        		"MissingSkillsException missingSkills parameter", 
        		"[" + opencrxKnowledge.getName() + ']',
        		cause.getParameter("missingSkills")
        	);        		
        	assertEquals(
        		"MissingSkillsException description", 
        		"One skill missing",
        		cause.getDescription()
        	);        		
        }
        //
        // Query Test
        //
        SkillQuery skillQuery = pkg.createSkillQuery();
        skillQuery.name().like("open.*");
        assertEquals("'open.*' skills", 2, segment.getSkill(skillQuery).size());
        skillQuery = pkg.createSkillQuery();
        skillQuery = pkg.createSkillQuery();
        skillQuery.name().equalTo("openMDX knowledge");	
        assertEquals("'openMDX knowledge' skill", 1, segment.getSkill(skillQuery).size());
        skillQuery = pkg.createSkillQuery();
        skillQuery.name().notEqualTo("openMDX knowledge");
        assertEquals("'openMDX knowledge' skill", 5, segment.getSkill(skillQuery).size());
        skillQuery = pkg.createSkillQuery();
        skillQuery.name().between("openHouse", "openSource");
        assertEquals("'openHouse .. openSource' skills", 1, segment.getSkill(skillQuery).size());
        skillQuery = pkg.createSkillQuery();
        skillQuery.name().outside("openHouse", "openSource");
        assertEquals("skills outside 'openHouse .. openSource'", 5, segment.getSkill(skillQuery).size());
        TeamMemberQuery teamMemberQuery = pkg.createTeamMemberQuery();
        teamMemberQuery.securityNumber().isNull();
        assertEquals("Without security number", 2, segment.getTeamMember(teamMemberQuery).size());
        ProjectQuery projectQuery = pkg.createProjectQuery();
        projectQuery.description().isNonNull();
        assertEquals("With description", 1, segment.getProject(projectQuery).size());
        projectQuery = pkg.createProjectQuery();
        projectQuery = pkg.createProjectQuery();
	}

	//-----------------------------------------------------------------------
	// Instance Members	
	//-----------------------------------------------------------------------

	protected final static Path TASK_IDENTITY = new Path(
		"xri:@openmdx:org.openmdx.example.workshop1/provider/:*/segment/:*/project/:*/task/:*"
    );

	/**
	 * The <code>PersistenceManagerFactory</code> belonging to the current fixture
	 */
	private PersistenceManager pm;
	
}
