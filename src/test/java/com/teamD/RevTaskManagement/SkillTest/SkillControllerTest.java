package com.teamD.RevTaskManagement.SkillTest;

import com.teamD.RevTaskManagement.controller.SkillController;
import com.teamD.RevTaskManagement.model.Skill;
import com.teamD.RevTaskManagement.service.SkillService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SkillControllerTest {

    @InjectMocks
    private SkillController skillController;

    @Mock
    private SkillService skillService;

    private Skill skill;

    @Before
    public void setup() {
        skill = new Skill();
        skill.setSkill("Java");
    }

    @Test
    public void testSetSkill() {
        when(skillService.createSkill(skill)).thenReturn(skill);

        ResponseEntity<BaseResponse<Skill>> response = skillController.setSkill(skill);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Skill added", response.getBody().getMessages());
        assertEquals(skill, response.getBody().getData());
    }

    @Test
    public void testFetchBySkill() {
        when(skillService.getSkillByName("Java")).thenReturn(skill);

        ResponseEntity<BaseResponse<Skill>> response = skillController.fetchBySkill("Java");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("fetched skill", response.getBody().getMessages());
        assertEquals(skill, response.getBody().getData());
    }
}