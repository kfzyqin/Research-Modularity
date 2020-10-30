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
        # path_1 = '/home/zhenyue-qin/Research/Project-Rin-Datasets/Project-Maotai-Data/Tec-Simultaneous-Experiments/distributional-tournament-no-x'

        # Zhenyue's 500, 2000
        # path_1 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-500-2000/mutation-0.2-x-0.2'
        # path_1 = '/Volumes/My Passport/IEEE-Alife/Zhenyue-500-2000/mutation-0.2-x-0.2'


        # Zhenyue's k or 500, 1000 no seed, random initial pop
        # path_1 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Zhenyue\'s-0.5-2000/mutation-0.2-x-0.2'

        # stochastic
        # path_1 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Stochastic-0.5-2000-random-init'
        # path_2 = "/Users/rouyijin/Desktop/IEEE-Alife-Results/stoc-x-0.2-0.2"
        # path_2 = '/Volumes/My Passport/IEEE-Alife/stoc-x-0.2-0.2'

        # Rouyi's max perturbation 10, without seed, 2 random
        # path_1 = '/Users/rouyijin/Desktop/IEEE-Alife-Results/Rouyi-selection-k-0.5-2000-max-10-with-perturbationMap'

        # Rouyi's max perturbation 2, without seed, 2 random
        # path_1 = '/Volumes/My Passport/IEEE-Alife/Rouyi-selection-k-0.5-2000-max-2-with-perturbationMap-random-init'

        # Rouyi's 500-2000
        # path_2 = '/Volumes/My Passport/IEEE-Alife/Rouyi-selection-500-2000-perturbationMap-random-init'

        # 3 targets max per 2 and 4
        # path_1 = '/Users/rouyijin/Desktop/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_2_4_3_targets'
        # path_2 = '/Users/rouyijin/Desktop/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_2_4_3_targets'
        # path_2 = '/Users/rouyijin/Desktop/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_4_3_targets'

        # 2 targets max per 2 partial
        # path_1 = "/Users/rouyijin/Desktop/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_partial_2_3_targets"
        # path_2 = "/Users/rouyijin/Desktop/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_partial_2_3_targets"

        # 3 targets partial max 2
        # path_1 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dyn_prog_weighted_partial_per2_3targets'
        # path_2 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dyn_prog_weighted_partial_per2_3targets'

        # 3 targets full per 2
        # path_1 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_2_4_3_targets'
        # path_2 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_2_4_3_targets'

        # sampling 3 targets
        # path_1 = '/Users/rouyijin/Desktop/sampling3/generated-outputs/stoc_fitness_dyn_prog_weighted_sampling_3targets'
        # path_2 = '/Users/rouyijin/Desktop/sampling3/generated-outputs/stoc_fitness_dyn_prog_weighted_sampling_3targets'

        # 3 targets
        path_1 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_4_3_targets'
        path_2 = '/Volumes/My Passport/target_3_results/generated-outputs/stoc_fitness_dynamic_prog_0.5_maxPer_4_3_targets'

        # sampling 2 targets
        # path_1 = '/Volumes/My Passport/2_targets/generated-outputs/stoc_fitness_dyn_prog_weighted_partial_per2_2targets'
        # path_2 = '/Volumes/My Passport/2_targets/generated-outputs/stoc_fitness_dyn_prog_weighted_partial_per2_2targets'

        omega = StatisticsToolkit(path_1, path_2)

        sample_size = 44

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
