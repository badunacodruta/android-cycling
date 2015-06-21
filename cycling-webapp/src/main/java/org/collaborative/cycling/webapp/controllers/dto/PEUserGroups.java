package org.collaborative.cycling.webapp.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PEUserGroups {
    @JsonProperty("groups")
    public List<GroupDetails> groups;
}
