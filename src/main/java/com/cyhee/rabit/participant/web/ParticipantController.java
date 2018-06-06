
package com.cyhee.rabit.participant.web;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cyhee.rabit.participant.model.Participant;
import com.cyhee.rabit.participant.service.ParticipantService;
import com.cyhee.rabit.web.model.ApiResponseEntity;

@RestController
@RequestMapping("rest/v1/participants")
public class ParticipantController {
	@Resource(name="basicParticipantService")
	private ParticipantService participantService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ApiResponseEntity<Iterable<Participant>> getAllParticipants() {
        return new ApiResponseEntity<Iterable<Participant>>(participantService.getAllParticipants(), HttpStatus.OK);
    }
	
	@RequestMapping(method=RequestMethod.POST)
    public ApiResponseEntity<Void> addParticipant(@RequestBody Participant participant) {
    	participantService.addParticipant(participant);
        return new ApiResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ApiResponseEntity<Participant> getParticipant(@PathVariable long id) {
    	return new ApiResponseEntity<>(participantService.getParticipant(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ApiResponseEntity<Void> updateParticipant(@PathVariable long id, @RequestBody Participant participantForm) {
    	participantService.updateParticipant(id, participantForm);
        return new ApiResponseEntity<>(HttpStatus.OK); 
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ApiResponseEntity<Void> deleteParticipant(@PathVariable long id) {
    	participantService.deleteParticipant(id);
        return new ApiResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
}
