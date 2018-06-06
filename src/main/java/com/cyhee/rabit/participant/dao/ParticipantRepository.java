
package com.cyhee.rabit.participant.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.cyhee.rabit.participant.model.Participant;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ParticipantRepository extends PagingAndSortingRepository<Participant, Long> {
}
