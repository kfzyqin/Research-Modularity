import scipy
import scipy.stats
import GRNPlotter
from GRNCSVReader import GRNCSVReader
from EdgeNumberTool import EdgeNumberTool
import matplotlib.pyplot as plt
import file_processor as fp


class StatisticsToolkit:
    def __init__(self, path_1=None, path_2=None):
        self.path_1 = path_1
        self.path_2 = path_2

    def calculate_modularity_significance(self, sample_size=40):
        grn_plotter = GRNPlotter.GRNPlotter()
        modularity_values_1 = grn_plotter.get_module_values_of_an_experiment(self.path_1, -1)[:sample_size]
        modularity_values_2 = grn_plotter.get_module_values_of_an_experiment(self.path_2, -1)[:sample_size]
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

    def calculate_most_modularities_significance(self, sample_size=40, index=-1):
        fitness_plotter = GRNCSVReader()
        most_mod_values_1 = fitness_plotter.get_most_modularities_of_an_experiment(self.path_1, index)[:sample_size]
        most_mod_values_2 = fitness_plotter.get_most_modularities_of_an_experiment(self.path_2, index)[:sample_size]
        print "length 1: ", len(most_mod_values_1), " length 2: ", len(most_mod_values_2)
        return self.calculate_statistical_significances(most_mod_values_1, most_mod_values_2)

    def calculate_fittest_modularities_significance(self, sample_size=40, index=-1):
        fitness_plotter = GRNCSVReader()
        most_mod_values_1 = fitness_plotter.get_fittest_modularities_of_an_experiment(self.path_1, index)[:sample_size]
        most_mod_values_2 = fitness_plotter.get_fittest_modularities_of_an_experiment(self.path_2, index)[:sample_size]
        print "length 1: ", len(most_mod_values_1), " length 2: ", len(most_mod_values_2)
        return self.calculate_statistical_significances(most_mod_values_1, most_mod_values_2)

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
        inter_module_edge_1 = edge_number_tool.analyze_inter_module_edges_for_an_experiment(
            self.path_1, phenotype_type, eva_type=eva_type, sample_size=sample_size,
            start_gen=start_gen, end_gen=end_gen)[:sample_size]
        inter_module_edge_2 = edge_number_tool.analyze_inter_module_edges_for_an_experiment(
            self.path_2, phenotype_type, eva_type=eva_type, sample_size=sample_size,
            start_gen=start_gen, end_gen=end_gen)[:sample_size]

        if to_plot:
            print(fp.get_distribution_of_a_list(inter_module_edge_1))
            print(fp.get_distribution_of_a_list(inter_module_edge_2))

            fp.plot_bar_charts([inter_module_edge_1, inter_module_edge_2], ['sym', 'asym'])

        print "length 1: ", len(inter_module_edge_1), " length 2: ", len(inter_module_edge_2)
        return self.calculate_statistical_significances(inter_module_edge_1, inter_module_edge_2)




