package sample.service;

import sample.models.Calories;
import sample.models.Macros;

public interface MacrosService {

    Macros caloriesToMacros(Calories calories);
}
