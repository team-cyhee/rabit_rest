package com.cyhee.rabit.web.notice;

import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.goal.Goal;
import com.cyhee.rabit.model.notice.Notice;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.service.cmm.AuthHelper;
import com.cyhee.rabit.service.cmm.ResponseHelper;
import com.cyhee.rabit.service.goal.GoalService;
import com.cyhee.rabit.service.goal.GoalStoreService;
import com.cyhee.rabit.service.notice.NoticeService;
import com.cyhee.rabit.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("rest/v1/notices")
public class NoticeController {
	@Resource(name="noticeService")
	private NoticeService noticeService;

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Notice>> getNotices(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(noticeService.getNotices(pageable), HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> addNotice(@RequestBody Notice notice) {
        noticeService.addNotice(notice);
        return ResponseHelper.createdEntity(ContentType.NOTICE, notice.getId());
    }

    @RequestMapping(value="/titles", method=RequestMethod.GET)
    public ResponseEntity<Page<Notice>> getNoticeTitles(@PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(noticeService.getNoticeTitles(pageable), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Notice> getNotice(@PathVariable long id) {
    	return new ResponseEntity<>(noticeService.getNotice(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNotice(@PathVariable long id, @PageableDefault(sort={"createDate"}, direction=Direction.DESC) Pageable pageable) {
    	noticeService.deleteNotice(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
