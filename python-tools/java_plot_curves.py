import numpy as np
from evo_alg_evals import fitness
from GRNPlotter import GRNPlotter
import file_processor as fp
import sys
import ast
import csv_reader

# working_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/soto/2018-07-02-20-27-16'
working_path = sys.argv[1]
perturbation_no = int(sys.argv[2])
str_targets = sys.argv[3]
target_thresholds = ast.literal_eval(sys.argv[4])

java_targets = np.array(ast.literal_eval(str_targets))


def get_current_targets(cur_gen, thresholds, targets):
    threshold_id = len(thresholds)
    actual_threshold_id = threshold_id

    for a_threshold in reversed(thresholds):
        if cur_gen > a_threshold:
            actual_threshold_id = threshold_id
            break
        else:
            if threshold_id != 1:
                threshold_id -= 1
            else:
                actual_threshold_id = threshold_id
                break
    return targets[:actual_threshold_id, :]


def generate_fitness_mod_graph(grns, targets, perturbations, file_name):
    fitness_values = []
    mod_values = []

    cur_idx = 0
    for a_fit_grn in grns:
        a_np_fit_grn = np.array(a_fit_grn).reshape(10, 10)
        cur_targets = get_current_targets(cur_idx, target_thresholds, targets)
        fitness_values.append(fitness.evaluate_grn(a_np_fit_grn, cur_targets, perturbations))
        a_grn_plotter = GRNPlotter()
        mod_values.append(a_grn_plotter.get_modularity_value(a_fit_grn))
        cur_idx += 1

    fp.save_lists_graph([fitness_values, mod_values], ['Fitness', 'Modularity'],
                        path=working_path, file_name=file_name)


mod_fit_list = csv_reader.get_entry_values_of_an_trial(working_path, 'MostModFitness')
mod_mod_list = csv_reader.get_entry_values_of_an_trial(working_path, 'MostModularity')
fit_fit_list = csv_reader.get_entry_values_of_an_trial(working_path, 'Best')
fit_mod_list = csv_reader.get_entry_values_of_an_trial(working_path, 'FittestModularity')

fp.save_lists_graph([mod_fit_list, mod_mod_list], ['Fitness', 'Modularity'], path=working_path, file_name='ModTrend.png')
fp.save_lists_graph([fit_fit_list, fit_mod_list], ['Fitness', 'Modularity'], path=working_path, file_name='FitTrend.png')