package com.cyhee.rabit.service.notice;

import com.cyhee.rabit.dao.notice.NoticeRepository;
import com.cyhee.rabit.exception.cmm.NoSuchContentException;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.notice.Notice;
import com.cyhee.rabit.service.cmm.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("noticeService")
public class NoticeService {
	@Autowired
	private NoticeRepository noticeRepository;

	public Page<Notice> getNotices(Pageable pageable) {
		return noticeRepository.findAll(pageable);
	}

	public Page<Notice> getNoticeTitles(Pageable pageable) {
		return noticeRepository.findAllTitles(ContentStatus.visible(), pageable);
	}

	public Notice getNotice(long id) {
		Optional<Notice> notice = noticeRepository.findById(id);
		if(!notice.isPresent())
			throw new NoSuchContentException(ContentType.NOTICE, id);
		return notice.get();
	}

	public Page<Notice> getNoticesByStatusIn(List<ContentStatus> statusList, Pageable pageable) {
		return noticeRepository.findAllByStatusIn(statusList, pageable);
	}

	public void addNotice(Notice notice) {
		noticeRepository.save(notice);
	}

	public void deleteNotice(long id) {
		Notice notice = getNotice(id);

		AuthHelper.isAuthorOrAdmin(notice);

		delete(notice);
	}

	void delete(Notice notice) {
		notice.setStatus(ContentStatus.DELETED);
		noticeRepository.save(notice);
	}
}
