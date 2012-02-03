package org.cloudme.notepad.meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

import lombok.val;

import org.cloudme.notepad.note.Note;
import org.cloudme.notepad.note.NoteService;
import org.cloudme.sugar.AbstractService;
import org.cloudme.sugar.Id;

import com.google.inject.Inject;

public class MeetingService extends AbstractService<Meeting> {

    private final MeetingDao dao;
    @Inject private NoteService noteService;

    @Inject
    public MeetingService(MeetingDao dao) {
        super(dao);
        this.dao = dao;
    }

    public Iterable<String> findAllTopics() {
        val topics = new TreeSet<String>();
        for (val meeting : dao.findAllOrderByTopic()) {
            topics.add(meeting.getTopic());
        }
        if (topics.isEmpty()) {
            // some sample topics
            topics.addAll(Arrays.asList("Project Status", "Team Meeting", "DB Setup"));
        }
        return topics;
    }

    public void create(Note note, Date date, String topic) {
        Meeting meeting = dao.findSingleByDateAndTopic(date, topic);
        if (meeting == null) {
            meeting = new Meeting();
            meeting.setDate(date);
            meeting.setTopic(topic);
            dao.put(meeting);
        }
        note.setMeetingId(meeting.getId());
        noteService.put(note);
    }

    public void update(Note note, Date date, String topic) {
		val oldMeetingId = Id.of(Meeting.class, note.getMeetingId());

		Meeting newMeeting = dao.findSingleByDateAndTopic(date, topic);
        if (newMeeting == null) {
            newMeeting = dao.find(oldMeetingId);
            newMeeting.setDate(date);
            newMeeting.setTopic(topic);
            dao.put(newMeeting);
        }

        note.setMeetingId(newMeeting.getId());
        noteService.put(note);

        if (!newMeeting.getId().equals(oldMeetingId.value())) {
            if (!noteService.hasNotes(oldMeetingId)) {
                dao.delete(oldMeetingId);
            }
        }
    }

    /**
     * Removes the note from the meeting. Deletes the meeting if it contains no
     * more notes.
     * 
     * @param meetingId
     *            The id of the meeting.
     * @param noteId
     *            The id of the note.
     */
    public void remove(Id<Meeting, Long> meetingId, Id<Note, Long> noteId) {
        noteService.delete(noteId);
        if (noteService.listByMeetingId(meetingId).isEmpty()) {
            delete(meetingId);
        }
    }

	public Collection<MeetingGroup> getMeetingGrous() {
		val groups = new ArrayList<MeetingGroup>();
		MeetingGroup group = null;
		for (val meeting : findAll()) {
			if (group == null) {
				group = new MeetingGroup();
				groups.add(group);
			}
			group.add(meeting);
		}
		return groups;
	}
}
