from StatisticsToolkit import StatisticsToolkit
import os
if __name__ == '__main__':
    to_use = 'specific'
    if to_use == 'detailed':
        # prefix_path = os.path.expanduser("~")
        prefix_path = '/media/zhenyue-qin/New Volume/Data-Warehouse/Data-Experiments/Project-Maotai/tec-data/'
        # exp_types = ['distributional', 'stochastic', 'elite-distributional', 'elite-stochastic']
        # sym_types = ['p00', 'p01']

        exp_types = ['distributional']
        sym_types = ['p00', 'p01']

        exp_sym_types = []
        for an_exp_type in exp_types:
            for a_sym_type in sym_types:
                exp_sym_types.append(os.path.join(prefix_path, an_exp_type + '-' + a_sym_type))

        for path_1_idx in range(len(exp_sym_types)):
            for path_2_idx in range(path_1_idx+1, len(exp_sym_types)):
                print '\n'
                path_1 = exp_sym_types[path_1_idx]
                path_2 = exp_sym_types[path_2_idx]

                omega = StatisticsToolkit(path_1, path_2)

                sample_size = 100

                the_index = -1

                print "path 1", path_1
                print "path 2", path_2

                print('fitness significance')
                print omega.calculate_fitness_significance(sample_size=sample_size, index=the_index)

                print('most modularity significance')
                print omega.calculate_most_modularities_significance(sample_size=sample_size, index=the_index)

                print('fittest modularity significance')
                print omega.calculate_fittest_modularities_significance(sample_size=sample_size, index=the_index)

                print('modularity significance')
                print omega.calculate_modularity_significance(sample_size=sample_size, no_self_edge=False)

                print('edge number significance')
                print omega.calculate_edge_number_significance(sample_size=sample_size)

                # print('edge number std dev significance')
                # print omega.calculate_edge_number_std_dev_significance(sample_size=sample_size)
                #
                # print('inter module edge number significance')
                # print(omega.calculate_inter_module_edge_number_significance(phenotype_type='fit', sample_size=sample_size))
                #
                # print omega.calcuate_evaluated_inter_module_edge_number_significance(phenotype_type='fit', eva_type='mode',
                #                                                                      sample_size=sample_size, start_gen=501,
                #                                                                      end_gen=2000, to_plot=True)

    elif to_use == 'specific':
        path_1 = '/media/zhenyue-qin/New Volume/Data-Warehouse/Data-Experiments/Project-Maotai/tec-simultaneous-experiments/always-return-same-fit'
        path_2 = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Portal/generated-outputs/distributional-proportional'

        omega = StatisticsToolkit(path_1, path_2)

        sample_size = 100

        the_index = -1

        print "path 1", path_1
        print "path 2", path_2

        print('fitness significance')
        print omega.calculate_fitness_significance(sample_size=sample_size, index=the_index)

        print('most modularity significance')
        print omega.calculate_most_modularities_significance(sample_size=sample_size, index=the_index)

        print('fittest modularity significance')
        print omega.calculate_fittest_modularities_significance(sample_size=sample_size, index=the_index)

        print('modularity significance')
        print omega.calculate_modularity_significance(sample_size=sample_size, no_self_edge=False)

        print('edge number significance')
        print omega.calculate_edge_number_significance(sample_size=sample_size)

        # print('edge number std dev significance')
        # print omega.calculate_edge_number_std_dev_significance(sample_size=sample_size)
        #
        # print('inter module edge number significance')
        # print(omega.calculate_inter_module_edge_number_significance(phenotype_type='fit', sample_size=sample_size))
        #
        # print omega.calcuate_evaluated_inter_module_edge_number_significance(phenotype_type='fit', eva_type='mode',
        #                                                                      sample_size=sample_size, start_gen=501,
        #                                                                      end_gen=2000, to_plot=True)
