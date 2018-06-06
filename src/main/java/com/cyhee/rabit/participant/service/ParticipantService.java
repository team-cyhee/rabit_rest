
package com.cyhee.rabit.participant.service;

import com.cyhee.rabit.participant.model.Participant;

public interface ParticipantService {
	Iterable<Participant> getAllParticipants();
	
	void addParticipant(Participant user);
	
	Participant getParticipant(long id);
	
	void updateParticipant(long id, Participant participantForm);
	
	void deleteParticipant(long id);
}
