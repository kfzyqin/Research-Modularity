import CSVFileOpener
from file_processor import get_immediate_subdirectories
import math
import file_processor as fp


class EdgeNumberTool:
    def __init__(self):
        self.csv_file_opener = CSVFileOpener.CSVFileOpener()

    @staticmethod
    def get_inter_module_edge_number(a_grn):
        grn_side_size = int(math.sqrt(len(a_grn)))
        mid_side_size = (grn_side_size / 2)

        non_inter_genes = set()
        for i in range(0, mid_side_size):
            tmp_cross_index = i
            while (tmp_cross_index < mid_side_size * grn_side_size):
                non_inter_genes.add(tmp_cross_index)
                tmp_cross_index += grn_side_size

        for i in range(mid_side_size, grn_side_size):
            tmp_cross_index = i + mid_side_size * grn_side_size
            while (tmp_cross_index < grn_side_size * grn_side_size):
                non_inter_genes.add(tmp_cross_index)
                tmp_cross_index += grn_side_size

        inter_genes = set(list(range(len(a_grn)))) - non_inter_genes
        edge_no = 0
        for i in inter_genes:
            if a_grn[i] != 0:
                edge_no += 1
        return edge_no

    def get_average_inter_module_edges_for_an_experiment(self, sample_size, a_type, root_directory_path):
        grn_phenotypes = fp.get_last_grn_phenotypes(sample_size, a_type, root_directory_path)
        inter_edge_nos = []
        for a_phenotype in grn_phenotypes:
            inter_edge_nos.append(self.get_inter_module_edge_number(a_phenotype))
        return inter_edge_nos

    def get_average_edge_number_in_each_generation(self, working_path):
        average_edge_numbers = self.csv_file_opener.get_fitness_values_of_an_trial(working_path, 'AvgEdgeNumber')
        return average_edge_numbers

    def get_std_dev_edge_number_in_each_generation(self, working_path):
        std_dev_numbers = self.csv_file_opener.get_fitness_values_of_an_trial(working_path, 'StdDevEdgeNumber')
        return std_dev_numbers

    def get_average_edge_number_of_the_whole_trial(self, working_path):
        edge_numbers = self.get_average_edge_number_in_each_generation(working_path)
        try:
            return reduce(lambda x, y: x + y, edge_numbers) / len(edge_numbers)
        except RuntimeError:
            return 0

    def get_average_edge_number_std_dev_of_the_whole_trial(self, working_path):
        std_devs = self.get_std_dev_edge_number_in_each_generation(working_path)
        try:
            return reduce(lambda x, y: x + y, std_devs) / len(std_devs)
        except RuntimeError:
            return 0

    def get_average_edge_number_of_the_whole_experiment(self, working_path):
        avg_edge_numbers = []

        directories = get_immediate_subdirectories(working_path)
        for a_directory in directories:
            try:
                avg_edge_numbers.append(self.get_average_edge_number_of_the_whole_trial(a_directory))
            except RuntimeError:
                pass

        return avg_edge_numbers

    def get_average_std_dev_edge_number_of_the_whole_experiment(self, working_path):
        avg_edge_numbers = []

        directories = get_immediate_subdirectories(working_path)
        for a_directory in directories:
            try:
                avg_edge_numbers.append(self.get_average_edge_number_std_dev_of_the_whole_trial(a_directory))
            except RuntimeError:
                pass

        return avg_edge_numbers


# omega = EdgeNumberTool()

# path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/edge-number-bias-investigation-proportional'
# print len(omega.get_average_edge_number_of_the_whole_experiment(path))
