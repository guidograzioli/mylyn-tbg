package com.undebugged.mylyn.tbg.core;

import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * implementing classes provide a set of options that can be used to create option and drop down lists.
 */
public interface OptionProvider {

    public Set<Entry<String, String>> getOptions(TaskRepository repository);
}