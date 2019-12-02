import scipy
import scipy.stats
import GRNPlotter
from GRNCSVReader import GRNCSVReader
from EdgeNumberTool import EdgeNumberTool
import matplotlib.pyplot as plt
import file_processor as fp
import tools.mod_tools as mod_tools
from GRNPlotter import GRNPlotter
from CSVFileOpener import CSVFileOpener


class StatisticsToolkit:
    def __init__(self, path_1=None, path_2=None):
        self.path_1 = path_1
        self.path_2 = path_2
        self.grn_plotter = GRNPlotter()

    def calculate_modularity_significance(self, sample_size=40, no_self_edge=False, index=-1):
        modularity_values_1 = self.grn_plotter.get_module_values_of_an_experiment(self.path_1, -1, no_self_edge=no_self_edge)[:sample_size]
        modularity_values_2 = self.grn_plotter.get_module_values_of_an_experiment(self.path_2, -1, no_self_edge=no_self_edge)[:sample_size]
        # edge_nums_1 = self.grn_plotter.get_exp_fittest_grn_edge_num(self.path_1, index=index)
        # edge_nums_2 = self.grn_plotter.get_exp_fittest_grn_edge_num(self.path_2, index=index)
        # return self.calculate_normed_mods_significance(modularity_values_1, modularity_values_2, edge_nums_1, edge_nums_2)
        return self.calculate_statistical_significances(modularity_values_1, modularity_values_2)

    @staticmethod
    def calculate_statistical_significances(values_1, values_2):
        average_1 = sum(values_1) / float(values_1.__len__())
        average_2 = sum(values_2) / float(values_2.__len__())

        wilcoxon = scipy.stats.wilcoxon(values_1, values_2)
        t_test = scipy.stats.ttest_ind(values_1, values_2)
        mann_whitney = scipy.stats.mannwhitneyu(values_1, values_2)

        return {"size_1": len(values_1), "size_2": len(values_2), "average_1": average_1, "average_2": average_2,
                "wilcoxon": wilcoxon, "t_test": t_test, 'mann whitney: ': mann_whitney}

    def calculate_fitness_significance(self, sample_size=40, index=-1):
        fitness_plotter = GRNCSVReader()
        fitness_values_1 = fitness_plotter.get_fitness_values_of_an_experiment(self.path_1, index)[:sample_size]
        fitness_values_2 = fitness_plotter.get_fitness_values_of_an_experiment(self.path_2, index)[:sample_size]
        print "length 1: ", len(fitness_values_1), " length 2: ", len(fitness_values_2)
        return self.calculate_statistical_significances(fitness_values_1, fitness_values_2)

    def calculate_normed_mods_significance(self, mods_1, mods_2, edge_nums_1, edge_nums_2):
        normed_mods_1 = mod_tools.normalize_mod_q(mods_1, edge_nums_1)
        normed_mods_2 = mod_tools.normalize_mod_q(mods_2, edge_nums_2)
        return self.calculate_statistical_significances(normed_mods_1, normed_mods_2)

    def calculate_most_modularities_significance(self, sample_size=40, index=-1):
        fitness_plotter = GRNCSVReader()
        most_mod_values_1 = fitness_plotter.get_most_modularities_of_an_experiment(self.path_1, index)[:sample_size]
        most_mod_values_2 = fitness_plotter.get_most_modularities_of_an_experiment(self.path_2, index)[:sample_size]
        # edge_nums_1 = self.grn_plotter.get_exp_fittest_grn_edge_num(self.path_1, index=index)
        # edge_nums_2 = self.grn_plotter.get_exp_fittest_grn_edge_num(self.path_2, index=index)
        # return self.calculate_normed_mods_significance(most_mod_values_1, most_mod_values_2, edge_nums_1, edge_nums_2)
        return self.calculate_statistical_significances(most_mod_values_1, most_mod_values_2)

    def calculate_fittest_modularities_significance(self, sample_size=40, index=-1):
        csv_file_opener = CSVFileOpener()
        mods_1, stdevs_1 = csv_file_opener.get_fittest_mod_of_a_exp_with_stdev(self.path_1, index, sample_size)
        mods_2, stdevs_2 = csv_file_opener.get_fittest_mod_of_a_exp_with_stdev(self.path_2, index, sample_size)
        return self.calculate_statistical_significances(mods_1, mods_2)

    def calculate_edge_number_significance(self, sample_size=40, index=-1):
        edge_number_tool = EdgeNumberTool()
        edge_number_1 = edge_number_tool.get_average_edge_number_of_the_whole_experiment(self.path_1)[:sample_size]
        edge_number_2 = edge_number_tool.get_average_edge_number_of_the_whole_experiment(self.path_2)[:sample_size]
        print "length 1: ", len(edge_number_1), " length 2: ", len(edge_number_2)
        return self.calculate_statistical_significances(edge_number_1, edge_number_2)

    def calculate_edge_number_std_dev_significance(self, sample_size=40, index=-1):
        edge_number_tool = EdgeNumberTool()
        std_dev_1 = edge_number_tool.get_average_std_dev_edge_number_of_the_whole_experiment(self.path_1)[:sample_size]
        std_dev_2 = edge_number_tool.get_average_std_dev_edge_number_of_the_whole_experiment(self.path_2)[:sample_size]
        print "length 1: ", len(std_dev_1), " length 2: ", len(std_dev_2)
        return self.calculate_statistical_significances(std_dev_1, std_dev_2)

    def calculate_inter_module_edge_number_significance(self, phenotype_type, sample_size=40):
        edge_number_tool = EdgeNumberTool()
        inter_module_edge_1 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(
            self.path_1, phenotype_type, sample_size=sample_size)[:sample_size]
        inter_module_edge_2 = edge_number_tool.get_average_inter_module_edges_for_an_experiment(
            self.path_2, phenotype_type, sample_size=sample_size)[:sample_size]

        print(fp.get_distribution_of_a_list(inter_module_edge_1))
        print(fp.get_distribution_of_a_list(inter_module_edge_2))

        fp.plot_bar_charts([inter_module_edge_1, inter_module_edge_2], ['sym', 'asym'])

        print "length 1: ", len(inter_module_edge_1), " length 2: ", len(inter_module_edge_2)
        return self.calculate_statistical_significances(inter_module_edge_1, inter_module_edge_2)

    def calcuate_evaluated_inter_module_edge_number_significance(self, phenotype_type, eva_type, start_gen, end_gen,
                                                                 sample_size=40, to_plot=False):
        edge_number_tool = EdgeNumberTool()
        inter_module_edge_1 = edge_number_tool.analyze_edge_nums_for_an_experiment(
            self.path_1, phenotype_type, eva_type=eva_type, sample_size=sample_size,
            start_gen=start_gen, end_gen=end_gen)[:sample_size]
        inter_module_edge_2 = edge_number_tool.analyze_edge_nums_for_an_experiment(
            self.path_2, phenotype_type, eva_type=eva_type, sample_size=sample_size,
            start_gen=start_gen, end_gen=end_gen)[:sample_size]

        if to_plot:
            print(fp.get_distribution_of_a_list(inter_module_edge_1))
            print(fp.get_distribution_of_a_list(inter_module_edge_2))

            fp.plot_bar_charts([inter_module_edge_1, inter_module_edge_2], ['sym', 'asym'])

        print "length 1: ", len(inter_module_edge_1), " length 2: ", len(inter_module_edge_2)
        return self.calculate_statistical_significances(inter_module_edge_1, inter_module_edge_2)


if __name__ == '__main__':
    bal_softmax_micro = [0.89120692, 0.893103421, 0.893275857, 0.892069, 0.892241359, 0.894137919, 0.894655168,
                      0.890689671,
           0.890517235, 0.89172411]
    bal_softmax_macro = [0.895172417, 0.892069, 0.890172422, 0.88931036, 0.893793106, 0.89120692, 0.896034479, 0.890862048,
           0.892241359, 0.89362067]

    bal_pc_softmax_micro = [0.890489638, 0.893084705, 0.8924523, 0.891764, 0.891470969, 0.893691242, 0.894046605,
                       0.890159547,
           0.889997244, 0.891028225]
    bal_pc_softmax_macro = [0.894888461, 0.891061425, 0.889619529, 0.889407575, 0.893162251, 0.89056921, 0.895139, 0.890755475,
           0.891913235, 0.89319253]

    unb_softmax_micro = [0.891034484, 0.890517235, 0.893103421, 0.892758608, 0.893448293]
    unb_softmax_macro = [0.843545437, 0.843878865, 0.845666647, 0.848545551, 0.849878788]

    unb_pc_softmax_micro = [0.898448288, 0.897241354, 0.897069, 0.894655168, 0.899482787, ]
    unb_pc_softmax_macro = [0.879212141, 0.879212201, 0.877, 0.871545434, 0.878]

    print(StatisticsToolkit.calculate_statistical_significances(bal_softmax_micro[:5], bal_pc_softmax_micro[:5]))
    print(StatisticsToolkit.calculate_statistical_significances(unb_softmax_micro, unb_pc_softmax_micro))
    print(StatisticsToolkit.calculate_statistical_significances(bal_softmax_macro[:5], bal_pc_softmax_macro[:5]))
    print(StatisticsToolkit.calculate_statistical_significances(unb_softmax_macro, unb_pc_softmax_macro))

    mnist_bal_softmax_micro = [98.0100, 97.7600, 98.1200, 97.8600, 98.0400]
    mnist_bal_softmax_macro = [0.9800, 0.9775, 0.9810, 0.9785, 0.9805]

    mnist_bal_pc_softmax_micro = [97.9200, 97.7800, 98.1200, 97.7200, 98.0600]
    mnist_bal_pc_softmax_macro = [0.9793, 0.9778, 0.9811, 0.9771, 0.9805]

    mnist_unb_softmax_micro = [96.9203, 96.8898, 96.6916, 96.8288, 96.7221]
    mnist_unb_softmax_macro = [0.9508, 0.9543, 0.9505, 0.9517, 0.9454]

    mnist_unb_pc_softmax_micro = [97.2557, 97.0422, 96.2494, 96.7526, 97.0270]
    mnist_unb_pc_softmax_macro = [0.9654, 0.9610, 0.9590, 0.9642, 0.9656]

    print(StatisticsToolkit.calculate_statistical_significances(mnist_bal_softmax_micro[:5], mnist_bal_pc_softmax_micro[:5]))
    print(StatisticsToolkit.calculate_statistical_significances(mnist_unb_softmax_micro, mnist_unb_pc_softmax_micro))
    print(StatisticsToolkit.calculate_statistical_significances(mnist_bal_softmax_macro[:5], mnist_bal_pc_softmax_macro[:5]))
    print(StatisticsToolkit.calculate_statistical_significances(mnist_unb_softmax_macro, mnist_unb_pc_softmax_macro))


