import scipy
import scipy.stats
import GRNPlotter
from FitnessPlotter import FitnessPlotter


class StatisticsToolkit:
    def __init__(self, path_1=None, path_2=None):
        self.path_1 = path_1
        self.path_2 = path_2

    def calculate_modularity_significance(self):
        grn_plotter = GRNPlotter.GRNPlotter()
        modularity_values_1 = grn_plotter.get_module_values_of_an_experiment(self.path_1, -1)[:40]
        modularity_values_2 = grn_plotter.get_module_values_of_an_experiment(self.path_2, -1)[:40]
        return self.calculate_statistical_significances(modularity_values_1, modularity_values_2)

    @staticmethod
    def calculate_statistical_significances(values_1, values_2):
        average_1 = sum(values_1) / float(values_1.__len__())
        average_2 = sum(values_2) / float(values_2.__len__())

        wilcoxon = scipy.stats.wilcoxon(values_1, values_2)
        t_test = scipy.stats.ttest_ind(values_1, values_2)
        return {"size_1": len(values_1), "size_2": len(values_2), "average_1": average_1, "average_2": average_2,
                "wilcoxon": wilcoxon, "t_test": t_test}

    def calculate_fitness_significance(self):
        fitness_plotter = FitnessPlotter()
        fitness_values_1 = fitness_plotter.get_fitness_values_of_an_experiment(self.path_1, -1)[:40]
        fitness_values_2 = fitness_plotter.get_fitness_values_of_an_experiment(self.path_2, -1)[:40]
        print "length 1: ", len(fitness_values_1), " length 2: ", len(fitness_values_2)
        return self.calculate_statistical_significances(fitness_values_1, fitness_values_2)
