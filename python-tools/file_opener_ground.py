from CSVFileOpener import CSVFileOpener
import file_processor as fp
import os
import numpy as np
import tools.storage.fitness_warehouse as fitness_warehouse

prefix_path = os.path.expanduser("~")


def plot_fit_lines(path_1, labels, save_path, file_name, sample_size=100, gens=2000, to_save=True):
    csv_file_opener = CSVFileOpener()
    target_column = None
    if 'Fitness' in labels[0]:
        if 'Stoc' in labels[0]:
            if 'Sym' in labels[0]:
                values = fitness_warehouse.stoc_p00_by_dist_p00_gen[:gens]
                value_std_1 = fitness_warehouse.stoc_p00_by_dist_p00_stdev[:gens]
            elif 'Asym' in labels[0]:
                values = fitness_warehouse.stoc_p01_by_dist_p01_gen[:gens]
                value_std_1 = fitness_warehouse.stoc_p01_by_dist_p01_stdev[:gens]
        else:
            target_column = 'Best'
            values, value_std_1 = csv_file_opener. \
                get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_1, target_column,
                                                                                   sample_size=sample_size)
    elif 'Modularity' in labels[0]:
        target_column = 'FittestModularity'
        values, value_std_1 = csv_file_opener. \
            get_mod_of_each_generation_in_a_whole_exp_with_stdev(path_1, target_column,
                                                                               sample_size=sample_size)

        # values, value_std_1 = csv_file_opener. \
        #     get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_1, target_column,
        #                                                                        sample_size=sample_size)

    print('current label: ', labels)
    print('final value: ', values[-1])
    print('max value: ', np.max(values))
    print('min value: ', np.min(values))
    print('len of values: ', len(values))

    if to_save:
        fp.save_lists_graph([values],
                            labels=labels,
                            ver_lines=[500], path=save_path, file_name=file_name, marker='.', colors=None,
                            dpi=500, to_normalize=False, x_gap=20, error_bars=[value_std_1], leg_loc='lower right')

path_dict = {
    'Dist Sym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p00',
    'Dist Asym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p01',
    'Elite Dist Sym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/elite-distributional-p00',
    'Elite Dist Asym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/elite-distributional-p01',
    'Stoc Sym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/stochastic-p00',
    'Stoc Asym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/stochastic-p01',
    'Elite Stoc Sym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/elite-stochastic-p00',
    'Elite Stoc Asym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/elite-stochastic-p01',
}



save_path = '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/tec-imgs'

sample_size = 100

path_key = 'Dist Sym'
value_type = 'Modularity'
# value_type = 'Modularity'
label_key = path_key + ' ' + value_type
plot_fit_lines(path_dict[path_key], [label_key], save_path, file_name=label_key + '.png', sample_size=sample_size)

# # fits_avg_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'Median')
# # fits_avg_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'Median')
#
# mod_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')
# mod_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'FittestModularity')
#
# fp.save_lists_graph([mod_1, mod_2], labels=['Distributional', 'Stochastic'], ver_lines=[500], path=path_1, file_name='avg_mod_comp_esw_new_p01_balanced.png', marker='.',
#                     colors=None, dpi=500, to_normalize=False)
#
# # fp.save_lists_graph([mod_1, mod_2], labels=['With Selection', 'No Selection'], ver_lines=None, path=path_1, file_name='avg_mod_perfect_module.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
#
# avg_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'AvgEdgeNumber')
# avg_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'AvgEdgeNumber')
#
# fp.save_lists_graph([avg_edge_no_1, avg_edge_no_2], labels=['Distributional', 'Stochastic'], ver_lines=[500], path=path_1, file_name='avg_edge_comp_esw_new_p01_balanced.png', marker='.',
#                     colors=None, dpi=500, to_normalize=False)
#
# # std_dev_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'StdDevEdgeNumber')
# # std_dev_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'StdDevEdgeNumber')
# #
# # fp.save_lists_graph([fits_avg_1, fits_avg_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='avg_median_fit_sym_asym_01.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
#
# #
# # fp.save_lists_graph([std_dev_edge_no_1, std_dev_edge_no_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='std_dev_edge_no_sym_asym_01.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
