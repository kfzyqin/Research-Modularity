import scipy
import scipy.stats


class StatisticsToolkit:
    def __init__(self, path_1, path_2):
        self.path_1 = path_1
        self.path_2 = path_2

    def calculate_statistical_significances(self):
        average_1 = sum(self.path_1) / float(self.path_1.__len__())
        average_2 = sum(self.path_2) / float(self.path_2.__len__())

        wilcoxon = scipy.stats.wilcoxon(self.path_1, self.path_2)
        t_test = scipy.stats.ttest_ind(self.path_1, self.path_2)
        return {"average_1": average_1, "average_2": average_2, "wilcoxon": wilcoxon, "t_test": t_test}





