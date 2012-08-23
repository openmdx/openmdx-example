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
		assignment.refInitialize(false, false);
		assignment.setTeamMember(
			params.getTeamMember()
		);
		assignment.setMemberRole(params.getMemberRole());
		this.sameObject().addAssignment(
		    false,
		    UUIDConversion.toUID(UUIDs.getGenerator().next()),
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
