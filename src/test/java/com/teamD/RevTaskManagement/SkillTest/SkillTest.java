package com.teamD.RevTaskManagement.SkillTest;

import com.teamD.RevTaskManagement.dao.SkillDAO;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.model.Skill;
import com.teamD.RevTaskManagement.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
public class SkillTest {
    @Mock
    private SkillDAO skillDAO;

    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSkill() {
        Skill skill = new Skill();
        skill.setSkill("Java");

        when(skillDAO.save(skill)).thenReturn(skill);

        Skill createdSkill = skillService.createSkill(skill);

        assertEquals(skill, createdSkill);
    }

    @Test
    void testGetSkillByName_Success() {
        String skillName = "Java";
        Skill skill = new Skill();
        skill.setSkill(skillName);

        when(skillDAO.findBySkill(skillName)).thenReturn(skill);

        Skill retrievedSkill = skillService.getSkillByName(skillName);

        assertEquals(skill, retrievedSkill);
    }

    @Test
    void testGetSkillByName_NotFound() {
        String skillName = "Python";

        when(skillDAO.findBySkill(skillName)).thenReturn(null);

        NotFoundException thrownException = assertThrows(
                NotFoundException.class,
                () -> skillService.getSkillByName(skillName),
                "Expected getSkillByName() to throw NotFoundException"
        );

        assertEquals("No skill with name: " + skillName, thrownException.getMessage());
    }

}
