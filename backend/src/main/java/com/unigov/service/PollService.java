package com.unigov.service;

import com.unigov.dto.PollDtos.*;
import com.unigov.entity.Poll;
import com.unigov.entity.PollOption;
import com.unigov.entity.User;
import com.unigov.repository.PollOptionRepository;
import com.unigov.repository.PollRepository;
import com.unigov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {
        @Autowired
        private PollRepository pollRepository;

        @Autowired
        private PollOptionRepository pollOptionRepository;

        @Autowired
        private UserRepository userRepository;

        @Transactional
        public PollResponse createPoll(PollRequest request, String username) {
                User creator = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Poll poll = new Poll();
                poll.setQuestion(request.getQuestion());
                poll.setCreator(creator);
                poll.setActive(true);

                List<PollOption> options = request.getOptions().stream()
                                .map(opt -> {
                                        PollOption po = new PollOption();
                                        po.setText(opt);
                                        po.setPoll(poll);
                                        po.setVotes(0);
                                        return po;
                                })
                                .collect(Collectors.toList());

                poll.setOptions(options);
                Poll saved = pollRepository.save(poll);
                return mapToResponse(saved);
        }

        public List<PollResponse> getAllPolls() {
                return pollRepository.findAll().stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Transactional
        public void vote(String optionId) {
                PollOption option = pollOptionRepository.findById(optionId)
                                .orElseThrow(() -> new RuntimeException("Option not found"));
                option.setVotes(option.getVotes() + 1);
                pollOptionRepository.save(option);
        }

        private PollResponse mapToResponse(Poll poll) {
                List<OptionResponse> items = poll.getOptions() != null ? poll.getOptions().stream()
                                .filter(java.util.Objects::nonNull)
                                .map(o -> OptionResponse.builder()
                                                .id(o.getId())
                                                .text(o.getText())
                                                .votes(o.getVotes())
                                                .build())
                                .collect(Collectors.toList()) : java.util.Collections.emptyList();

                return PollResponse.builder()
                                .id(poll.getId())
                                .question(poll.getQuestion())
                                .active(poll.isActive())
                                .createdAt(poll.getCreatedAt())
                                .options(items)
                                .build();
        }
}
