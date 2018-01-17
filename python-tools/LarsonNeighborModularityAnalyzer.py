from GRNPlotter import GRNPlotter
from file_processor import get_immediate_subdirectories
from GRNEdgeMutator import GRNEdgeMutator
import copy

class LarsonNeighborModularityAnalyzer:
    def __init__(self, path=None, neighbor_size=10):
        self.path = path
        self.grn_plotter = GRNPlotter()
        self.directories = get_immediate_subdirectories(self.path)
        self.grn_edge_mutator = GRNEdgeMutator()
        self.neighbor_size = neighbor_size

    def get_last_generation_phenotypes(self):
        last_phenotypes = []
        for a_directory in self.directories:
            last_phenotypes.append(self.grn_plotter.get_grn_phenotypes(a_directory)[-1])
        return last_phenotypes

    def get_mutated_neighbor_modularity_values(self, grn):
        mutated_neighbors = []
        modularity_values = []
        for i in range(self.neighbor_size):
            mutated_neighbors.append(copy.copy(grn))

        for i in range(1, self.neighbor_size):
            self.grn_edge_mutator.mutateAGRN(mutated_neighbors[i])

        for a_grn_phenotype in mutated_neighbors:
            modularity_values.append(self.grn_plotter.get_modularity_value(self.grn_plotter.generate_directed_grn(a_grn_phenotype)))
        print "original: ", modularity_values[0]
        print "max: ", max(modularity_values)
        return modularity_values

    def get_max_mutated_modularity_values(self):
        phenotypes = self.get_last_generation_phenotypes()
        max_mutated_modularity_values = []
        for a_phenotype in phenotypes:
            max_mutated_modularity_values.append(max(self.get_mutated_neighbor_modularity_values(a_phenotype)))
        return max_mutated_modularity_values

omega = LarsonNeighborModularityAnalyzer(path='/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording')
print omega.get_max_mutated_modularity_values()
