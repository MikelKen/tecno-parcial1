package com.example.tecno_proyect.service;

import com.example.tecno_proyect.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandProcessor {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private DesignService designService;
    @Autowired
    private PayPlanService payPlanService;
    @Autowired
    private PaysService paysService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialProjectService materialProjectService;
    @Autowired
    private EmailResponseService emailResponseService;

    public String processCommand(String subject, String senderEmail) {
        return "Command processor working";
    }
}
