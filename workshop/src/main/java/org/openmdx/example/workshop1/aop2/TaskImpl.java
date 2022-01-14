/*
 * ====================================================================
 * Project:     openMDX/Workshop, http://www.openmdx.org/
 * Description: openMDX/Example Workshop 
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
package org.openmdx.example.workshop1.aop2;


import java.util.HashSet;
import java.util.Set;

import javax.jmi.reflect.RefException;

import org.openmdx.base.aop2.AbstractObject;
import org.openmdx.base.text.conversion.UUIDConversion;
import org.openmdx.example.workshop1.jmi1.AssignTeamMemberParams;
import org.openmdx.example.workshop1.jmi1.AssignTeamMemberResult;
import org.openmdx.example.workshop1.jmi1.MissingSkillException;
import org.openmdx.example.workshop1.jmi1.Skill;
import org.openmdx.example.workshop1.jmi1.TeamMemberAssignment;
import org.openmdx.example.workshop1.jmi1.Workshop1Package;
import org.openmdx.kernel.exception.BasicException;
import org.openmdx.kernel.id.UUIDs;

/**
 * openMDX/Example workshop1 Task implementation
 */
public class TaskImpl
	<S extends org.openmdx.example.workshop1.jmi1.Task,N extends org.openmdx.example.workshop1.cci2.Task,C extends Void>
	extends AbstractObject<S,N,C> {

    //------------------------------------------------------------------------
	/**
	 * Constructor
	 * 
	 * @param delegate this instance's delegate
	 */
	public TaskImpl(
        S same,
        N next
	) {
		super(same, next);
	}

    //------------------------------------------------------------------------
	/**
	 * Implement the modeled operation
	 */
	public AssignTeamMemberResult assignTeamMember(
		AssignTeamMemberParams params
	) throws RefException {
	    //
	    // Validate
	    //
		if(!params.getTeamMember().getSkill().containsAll(this.sameObject().getSkill())) {
			Set<Skill> skills = new HashSet<Skill>(this.sameObject().<Skill>getSkill());
			skills.removeAll(params.getTeamMember().getSkill());
			Set<String> missingSkills = new HashSet<String>();
			for(Skill skill: skills) {
			    missingSkills.add(skill.getName());
			}
			throw new MissingSkillException(
				"Workshop",
				BasicException.Code.BAD_PARAMETER,
                missingSkills.size() == 1 ? 
                	"One skill missing" :
                	"" + missingSkills.size() + " skills missing",
				missingSkills.toString()
			);
		}
		//
		// Execute
		//
		TeamMemberAssignment assignment = this.sameManager().newInstance(TeamMemberAssignment.class);
		assignment.setTeamMember(
			params.getTeamMember()
		);
		assignment.setMemberRole(params.getMemberRole());
		this.sameObject().addAssignment(
		    UUIDConversion.toUID(UUIDs.newUUID()),
		    assignment
		);
		//
		// Reply
		//
		return ((Workshop1Package)this.sameObject().refOutermostPackage().refPackage(
	       Workshop1Package.class.getName()
	    )).createAssignTeamMemberResult(assignment);
	}
	
	//------------------------------------------------------------------------
	// Members
	//------------------------------------------------------------------------	
    
}
