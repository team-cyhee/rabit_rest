
package com.cyhee.rabit.participant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyhee.rabit.participant.dao.ParticipantRepository;
import com.cyhee.rabit.participant.model.Participant;

@Service("basicParticipantService")
public class BasicParticipantService implements ParticipantService {
	@Autowired
	private ParticipantRepository participantRepository;

	public Iterable<Participant> getAllParticipants() {
		return participantRepository.findAll();
	}

	public void addParticipant(Participant participant) {
		participantRepository.save(participant);
	}

	public Participant getParticipant(long id) {
		return participantRepository.findById(id).get();
	}

	public void updateParticipant(long id, Participant participantForm) {
		Participant participant = participantRepository.findById(id).get();
		participant = participantForm;
		participantRepository.save(participant);
	}

	public void deleteParticipant(long id) {
		participantRepository.deleteById(id);
	}
	
}
