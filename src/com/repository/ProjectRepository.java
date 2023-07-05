package com.repository;

import java.util.ArrayList;
import java.util.Arrays;

public class ProjectRepository {
	private ArrayList<String> projects = new ArrayList<String>(Arrays.asList("projet1", "projet2"));
	
	public ArrayList<String> getAll() {
		return projects;
	}
}
