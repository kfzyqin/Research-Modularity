from CSVFileOpener import CSVFileOpener
import file_processor as fp
import os
import numpy as np
import tools.storage.fitness_warehouse as fitness_warehouse
from StatisticsToolkit import StatisticsToolkit
import data_storage.data_storage_2020 as data_storage_2020

prefix_path = os.path.expanduser("~")

where_ = None

def plot_fit_lines(path_1, labels, save_path, file_name, sample_size=100, gens=2000, to_save=True, target_column=None):
    csv_file_opener = CSVFileOpener()
    target_column = None
    if 'Fitness' in labels[0]:
        if 'Stoc' in labels[0]:
            if 'Sym' in labels[0]:
                if 'Elite' in labels[0]:
                    values = data_storage_2020.stoc_x_elite_p00[0:2000]
                    value_std_1 = data_storage_2020.stoc_x_elite_p00_stdev[0:2000]
                else:
                    values = data_storage_2020.stoc_x_p00[0:2000]
                    value_std_1 = data_storage_2020.stoc_x_p00_stdev[0:2000]
            elif 'Asym' in labels[0]:
                if 'Elite' in labels[0]:
                    values = data_storage_2020.stoc_x_elite_p01[0:2000]
                    value_std_1 = data_storage_2020.stoc_x_elite_p01_stdev[0:2000]
                else:
                    values = data_storage_2020.stoc_x_p01[0:2000]
                    value_std_1 = data_storage_2020.stoc_x_p01_stdev[0:2000]
        else:
            if 'Asym' in labels[0]:
                if 'Elite' in labels[0]:
                    values = data_storage_2020.dist_x_elite_p01[1:2001]
                    value_std_1 = data_storage_2020.dist_x_elite_p01_stdev[1:2001]
                else:
                    values = data_storage_2020.dist_x_p01[1:2001]
                    value_std_1 = data_storage_2020.dist_x_p01_stdev[1:2001]
            else:
                target_column = 'Best'
                values, value_std_1 = csv_file_opener. \
                    get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_1, target_column,
                                                                                       sample_size=sample_size)
                values = values[1:2001]
                value_std_1 = value_std_1[1:2001]
    elif 'Modularity' in labels[0]:
        target_column = 'FittestModularity'
        values, value_std_1 = csv_file_opener. \
            get_mod_of_each_generation_in_a_whole_exp_with_stdev(path_1, target_column,
                                                                               sample_size=sample_size)
        if 'Stoc' in labels[0]:
            values = values[0:2000]
        else:
            values = values[1:2001]

    elif 'Edge' in labels[0]:
        values, value_std_1 = csv_file_opener. \
            get_edge_num_of_each_generation_in_a_whole_exp_with_stdev(path_1, sample_size=sample_size)

        if 'Stoc' in labels[0]:
            values = values[0:2000]
        else:
            values = values[1:2001]

        # values, value_std_1 = csv_file_opener. \
        #     get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_1, target_column,
        #                                                                        sample_size=sample_size)

    print('current label: ', labels)
    print('final value: ', values[-1])
    print('value_std_1: ', value_std_1[499])
    print('max value: ', np.max(values))
    where_ = np.argmax(values)
    print 'where_', where_
    print('min value: ', np.min(values))
    print('len of values: ', len(values))

    if to_save:
        # if 'Stoc' in labels[0]:
        #     the_colors = [1, 0, 2, 3, 4, 5]
        # else:
        #     the_colors = None

        if 'Fitness' in labels[0]:
            a_y_lim = [0.4, 0.42, 0.44, 0.46, 0.48, 0.5, 0.52, 0.54, 0.56, 0.58,
                       0.6, 0.62, 0.64, 0.66, 0.68,
                       0.7, 0.72, 0.74, 0.76, 0.78,
                       0.8, 0.82, 0.84, 0.86, 0.88,
                       0.9, 0.92, 0.94, 0.96, 0.98,
                       1.0]
        elif 'Modularity' in labels[0]:
            a_y_lim = [-0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
                       1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0, 2.1, 2.2]
        fp.save_lists_graph([values],
                            labels=labels,
                            ver_lines=[500], path=save_path, file_name=file_name, marker='.',
                            dpi=100, to_normalize=False, x_gap=25, error_bars=[value_std_1], leg_loc='lower right',
                            y_lim=a_y_lim)

    return values, labels, value_std_1


path_dict = {
    'Dist Sym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-distributional-x-p00',
    # 'Dist Sym': '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p00',
    'Dist Asym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-distributional-x-p01',
    'Elite Dist Sym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-distributional-elite-x-p00',
    'Elite Dist Asym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-distributional-elite-x-p01',
    'Stoc Sym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-x-p00',
    'Stoc Asym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-x-p01',
    'Elite Stoc Sym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-elite-x-p00',
    'Elite Stoc Asym': '/media/zhenyue-qin/New Volume/Experiment-Data-Storage/Storage-Modularity/2020-New-Exps/2020-stochastic-elite-x-p01',
    # 'With Selection': '/media/zhenyue-qin/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/with-selection-two-targets',
    # 'No Selection': '/media/zhenyue-qin/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking'
    #                 '/generated-outputs/no-selection-two-targets',
    # 'Tour-No-X': '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments' \
    #              '/distributional-tournament-no-x',
    # 'Prop-No-X': '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments'
    #              '/distributional-proportional-no-x',
    # 'Dist-Prop': '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-proportional'
}


# save_path = '/home/zhenyue-qin/Research/Project-Nora-Miscellaneous/tmp-imgs'
save_path_single = 'tmp-imgs'
save_combined_path = 'tmp-imgs'

save_path_selection = 'tmp-imgs'

sample_size = 100

for a_key_1 in path_dict.keys():
    for a_key_2 in path_dict.keys():
        for a_value in ['Fitness', 'Modularity']:
            if a_key_1 != a_key_2:
                if 'Stoc' in a_key_1 and 'Dist' in a_key_2:
                    continue
                path_key_1 = a_key_1
                value_type = a_value
                label_key_1 = path_key_1 + ' ' + value_type
                label_key_1 = label_key_1.replace(' ', '-')

                values_1, label_1, stdevs_1 = plot_fit_lines(path_dict[path_key_1], [label_key_1], save_path_selection, file_name=label_key_1 + '.png',
                                                             sample_size=sample_size, to_save=True, gens=2000)

                path_key_2 = a_key_2
                label_key_2 = path_key_2 + ' ' + value_type
                label_key_2 = label_key_2.replace(' ', '-')
                values_2, label_2, stdevs_2 = plot_fit_lines(path_dict[path_key_2], [label_key_2], save_path_selection, file_name=label_key_2 + '.png',
                                                             sample_size=sample_size, to_save=True, gens=2000)

                stat_tool_kil = StatisticsToolkit()
                print stat_tool_kil.calculate_statistical_significances(values_1[1:2001], values_2[1:2001])

                to_save_name = path_key_1 + ' ' + path_key_2 + ' ' + value_type
                to_save_name = to_save_name.replace(' ', '_')

                if value_type == 'Fitness':
                    a_y_lim = [0.4, 0.42, 0.44, 0.46, 0.48, 0.5, 0.52, 0.54, 0.56, 0.58,
                               0.6, 0.62, 0.64, 0.66, 0.68,
                               0.7, 0.72, 0.74, 0.76, 0.78,
                               0.8, 0.82, 0.84, 0.86, 0.88,
                               0.9, 0.92, 0.94, 0.96, 0.98,
                               1.0]
                else:
                    a_y_lim = [-0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1,
                               1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0, 2.1, 2.2]
                fp.save_lists_graph([values_1[0:2000], values_2[0:2000]],
                                    labels=[path_key_1, path_key_2],
                                    ver_lines=[500], path=save_path_selection, file_name=to_save_name, marker='.', colors=[0, 1],
                                    dpi=100, to_normalize=False, x_gap=25, error_bars=[stdevs_1[0:2002], stdevs_2[0:2002]],
                                    leg_loc='lower right', y_lim=a_y_lim)


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
