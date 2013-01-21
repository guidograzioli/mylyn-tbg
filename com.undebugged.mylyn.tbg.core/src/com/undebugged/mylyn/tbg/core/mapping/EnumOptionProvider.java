package com.undebugged.mylyn.tbg.core.mapping;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * An OptionProvider that takes it's values from a String array.
 */
public class EnumOptionProvider implements OptionProvider{

    HashMap<String, String> options = new HashMap<String, String>();
    public EnumOptionProvider(String[] values) {
        for (String value : values) {
            options.put(value, value);
        }
    }
    @Override
    public Set<Entry<String, String>> getOptions(TaskRepository repository) {
        return options.entrySet();
    }

    
}