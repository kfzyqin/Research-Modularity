import CSVFileOpener
from file_processor import get_immediate_subdirectories


class EdgeNumberTool:
    def __init__(self):
        self.csv_file_opener = CSVFileOpener.CSVFileOpener()

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
