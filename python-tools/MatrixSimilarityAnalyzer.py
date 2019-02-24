import os
import file_processor as fp
import math
import numpy as np
import ast
from StatisticsToolkit import StatisticsToolkit
from scipy import spatial
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_samples, silhouette_score
from time import gmtime, strftime
from sklearn.preprocessing import StandardScaler
import seaborn as sn
import matplotlib.pyplot as plt


class MatrixSimilarityAnalyzer:
    def __init__(self):
        # self.prefix_path = os.path.expanduser("~")
        self.starting_path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p00'
        self.starting_path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-p01'

        # self.starting_path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p00'
        # self.starting_path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p01'

        self.sample_size = 20
        self.to_fetch_sample = 20
        self.population_size = 100
        self.grn_size = 100

    @staticmethod
    def convert_a_list_grn_to_a_matrix(a_grn_phenotype):
        grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
        return np.array(a_grn_phenotype).reshape([grn_side_size, grn_side_size])

    def evaluate_grn_distances(self, list_phenotypes, dist_type):
        matrix_phenotypes = list([self.convert_a_list_grn_to_a_matrix(a_phe) for a_phe in list_phenotypes])

        dists = []
        for i in range(len(matrix_phenotypes)):
            for j in range(i + 1, len(matrix_phenotypes)):
                if dist_type == 'manhattan':
                    dists.append(np.sum(abs(matrix_phenotypes[i] - matrix_phenotypes[j])))
                elif dist_type == 'cosine':
                    a_cos_dist = spatial.distance.cosine((np.asarray(matrix_phenotypes[i])).reshape(-1),
                                                         (np.asarray(matrix_phenotypes[j])).reshape(-1))
                    dists.append(a_cos_dist)
        return dists

    def evaluate_inter_file_grn_distances(self, a_type, root_directory_path, dist_type):
        list_phenotypes = fp.get_last_grn_phenotypes(self.sample_size, a_type, root_directory_path)
        return self.evaluate_grn_distances(list_phenotypes, dist_type)

    def get_pop_phe_lists_of_a_trial(self, file_path, starting_gen=0, ending_gen=None, the_interval=1):
        txt_files_unsorted = []
        phenotypes = []

        txt_file_prefix = ''
        for root, dirs, files in os.walk(file_path):
            for a_file in files:
                if a_file.endswith('.lists'):
                    txt_files_unsorted.append(root + os.sep + a_file)
                    if txt_file_prefix == '':
                        txt_file_prefix = root + os.sep

        if ending_gen is None:
            ending_gen = len(txt_files_unsorted)

        txt_files_sorted = []
        for txt_file_idx in range(len(txt_files_unsorted)):
            txt_files_sorted.append(txt_file_prefix + 'all-population-phenotype_gen_' + str(txt_file_idx) + '.lists')

        txt_files_sorted = txt_files_sorted[starting_gen:ending_gen]
        new_txt_files_sorted = []
        for i in range(0, len(txt_files_sorted), the_interval):
            new_txt_files_sorted.append(txt_files_sorted[i])

        txt_files_sorted = new_txt_files_sorted

        for a_txt_file in txt_files_sorted:
            a_gen_phe = []
            ind_lines = fp.read_a_file_line_by_line(a_txt_file)
            for an_ind_idx in range(0, len(ind_lines)):
                to_append = ast.literal_eval(ind_lines[an_ind_idx])
                if len(to_append) < 100:
                    print "watch: ", file_path
                a_gen_phe.append(to_append)
                # print("to_append: ", to_append)
            phenotypes.append(a_gen_phe)

        return phenotypes

    def get_pop_phe_lists_of_an_experiment(self, root_path, sample_size, starting_gen=0, ending_gen=None,
                                           the_interval=1):
        pop_phe_lists_list = []
        for a_trial_dir in fp.get_immediate_subdirectories(root_path, no_limitation=5)[:sample_size]:
            print(a_trial_dir)
            pop_phe_lists_list.append(self.get_pop_phe_lists_of_a_trial(a_trial_dir, starting_gen, ending_gen,
                                                                        the_interval=the_interval))
        return pop_phe_lists_list

    def evaluate_inter_ind_grn_distances(self, exp_list, dict_type, use_average=True):
        gen_dist_dict = {}
        for pop_phe_lists in exp_list:
            for i in range(len(pop_phe_lists)):
                if gen_dist_dict.has_key(i):
                    if use_average:
                        gen_dist_dict[i].append(np.mean(self.evaluate_grn_distances(pop_phe_lists[i], dict_type)))
                    else:
                        gen_dist_dict[i].append(self.evaluate_grn_distances(pop_phe_lists[i], dict_type))
                else:
                    if use_average:
                        gen_dist_dict[i] = [np.mean(self.evaluate_grn_distances(pop_phe_lists[i], dict_type))]
                    else:
                        gen_dist_dict[i] = [self.evaluate_grn_distances(pop_phe_lists[i], dict_type)]

        return gen_dist_dict

    @staticmethod
    def plot_dist_gen_curve(dist_dict, dpi=300, save_path=None, save_name=None, start_gen=0, end_gen=10000):
        plt.rcParams.update({'font.size': 22})
        dist_list = []
        if isinstance(dist_dict, dict):
            set_idxs = sorted(list([int(x) for x in dist_dict[0].keys()]))
            for a_gen in set_idxs[start_gen:end_gen]:
                dist_list.append(np.mean(dist_dict[str(a_gen)]))
            fp.plot_a_list_graph(dist_list, 'Avg dist', dpi=dpi, save_path=save_path, save_name=save_name, marker='x')

        elif isinstance(dist_dict, list):
            set_idxs = sorted(list([int(x) for x in dist_dict[0].keys()]))
            for a_dist_dict in dist_dict:
                a_dist_list = []
                for a_gen in set_idxs[start_gen:end_gen]:
                    a_dist_list.append(np.mean(a_dist_dict[str(a_gen)]))
                dist_list.append(a_dist_list)
            fp.save_lists_graph(dist_list, ['Symmetry', 'Asymmetry'], path=save_path, file_name=save_name, dpi=dpi, marker='x', left_lim=start_gen)

    def launch_inter_ind_dist_only_one(self, path_idx, starting_gen, dist_type, use_average=True, to_plot=False, end_gen=None,
                               the_interval=1, ):
        if path_idx == 1:
            exp_list = self.get_pop_phe_lists_of_an_experiment(self.starting_path_1, sample_size=self.to_fetch_sample,
                                                                 starting_gen=starting_gen, ending_gen=end_gen,
                                                                 the_interval=the_interval)
        elif path_idx == 2:
            exp_list = self.get_pop_phe_lists_of_an_experiment(self.starting_path_2, sample_size=self.to_fetch_sample,
                                                               starting_gen=starting_gen, ending_gen=end_gen,
                                                               the_interval=the_interval)

        if use_average:
            dist_dict = self.evaluate_inter_ind_grn_distances(exp_list, dist_type, use_average=True)


            cur_time = strftime("-%Y-%m-%d-%H-%M-%S", gmtime())

            if to_plot:
                import json
                with open('./generated_images/dict_' + str(path_idx) + cur_time + '.json', 'w') as a_f:
                    json.dump(dist_dict, a_f, sort_keys=True, indent=4)
                a_f.close()

                self.plot_dist_gen_curve(dist_dict, dpi=300,
                                         save_path='./generated_images/', save_name=('avg_dist_'  + str(path_idx) + cur_time + '.png'))


    def launch_inter_ind_dists(self, starting_gen, dist_type, use_average=True, to_plot=False, end_gen=None,
                               the_interval=1):
        exp_list_1 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_1, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen, ending_gen=end_gen,
                                                             the_interval=the_interval)
        exp_list_2 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_2, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen, ending_gen=end_gen,
                                                             the_interval=the_interval)

        if use_average:
            dist_dict_1 = self.evaluate_inter_ind_grn_distances(exp_list_1, dist_type, use_average=True)
            dist_dict_2 = self.evaluate_inter_ind_grn_distances(exp_list_2, dist_type, use_average=True)

            print('dist dict 1: ', dist_dict_1.keys())
            # print(dist_dict_2)

            set_idxs_1 = sorted(dist_dict_1.keys())
            set_idxs_2 = sorted(dist_dict_2.keys())

            cur_time = strftime("-%Y-%m-%d-%H-%M-%S", gmtime())

            if to_plot:
                import json
                with open('./generated_images/dict_1' + cur_time + '.json', 'w') as a_f:
                    json.dump(dist_dict_1, a_f, sort_keys=True, indent=4)
                a_f.close()

                with open('./generated_images/dict_2' + cur_time + '.json', 'w') as a_f:
                    json.dump(dist_dict_2, a_f, sort_keys=True, indent=4)
                a_f.close()

                self.avg_dist_of_each_gen_analyse(dist_dict_1, dist_dict_2)

                self.plot_dist_gen_curve(dist_dict_1, dpi=300,
                                         save_path='./generated_images/', save_name=('avg_dist_1' + cur_time + '.png'))
                self.plot_dist_gen_curve(dist_dict_2, dpi=300,
                                         save_path='./generated_images/', save_name=('avg_dist_2' + cur_time + '.png'))
                self.plot_dist_gen_curve([dist_dict_1, dist_dict_2], dpi=300,
                                         save_path='./generated_images/', save_name=('avg_dist_1_2' + cur_time + '.png'))

            print("len 1: ", len(dist_dict_1[set_idxs_1[-1]]))
            print("len 2: ", len(dist_dict_2[set_idxs_2[-1]]))
            print(StatisticsToolkit.calculate_statistical_significances(dist_dict_1[set_idxs_1[-1]][:self.sample_size],
                                                                        dist_dict_2[set_idxs_2[-1]][:self.sample_size]))

        else:
            dists_dict_1 = self.evaluate_inter_ind_grn_distances(exp_list_1, dist_type, use_average=False)
            dists_dict_2 = self.evaluate_inter_ind_grn_distances(exp_list_2, dist_type, use_average=False)

            set_idxs_1 = sorted(dists_dict_1.keys())
            set_idxs_2 = sorted(dists_dict_2.keys())

            for dists_1, dists_2 in zip(dists_dict_1[set_idxs_1[-1]][:self.sample_size],
                                        dists_dict_2[set_idxs_2[-1]][:self.sample_size]):
                print(StatisticsToolkit.calculate_statistical_significances(dists_1, dists_2))

    def statistically_compare_two_inter_file_grn_distances(self, a_type, dist_type):
        dists_1 = self.evaluate_inter_file_grn_distances(a_type, self.starting_path_1, dist_type)
        dists_2 = self.evaluate_inter_file_grn_distances(a_type, self.starting_path_2, dist_type)
        print(StatisticsToolkit.calculate_statistical_significances(dists_1, dists_2))

    def k_means_analysis(self, max_cluster, phenotypes):
        np_phenotypes = np.array(phenotypes)
        previous_score = -1
        for a_cluster in range(2, max_cluster):
            kmeans = KMeans(n_clusters=a_cluster, random_state=2)
            cluster_labels = kmeans.fit_predict(np_phenotypes)
            silhouette_avg = silhouette_score(np_phenotypes, cluster_labels)
            if silhouette_avg < previous_score:
                return a_cluster
            else:
                previous_score = silhouette_avg
        return max_cluster

    def evaluate_k_means_inter_ind(self, exp_list, max_cluster):
        cluster_nos = {}
        for pop_phe_lists in exp_list:
            for i in range(len(pop_phe_lists)):
                if cluster_nos.has_key(i):
                    cluster_nos[i].append(self.k_means_analysis(max_cluster, pop_phe_lists[i]))
                else:
                    cluster_nos[i] = [self.k_means_analysis(max_cluster, pop_phe_lists[i])]

        return cluster_nos

    def launch_clustering_evaluation(self, starting_gen):
        exp_list_1 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_1, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen)
        exp_list_2 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_2, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen)

        for an_idx in range(self.sample_size):
            exp_np_list_1 = np.array(exp_list_1[an_idx]).reshape(self.population_size, self.grn_size)
            exp_np_list_2 = np.array(exp_list_1[an_idx]).reshape(self.population_size, self.grn_size)

            # concat_vectors = np.concatenate([exp_np_list_1[:1000], exp_np_list_2[:1000]], axis=1)
            # print("concat vectors shape: ", concat_vectors.shape)

            scaler = StandardScaler()
            X_scaled = scaler.fit_transform(exp_np_list_1)
            cmap = sn.cubehelix_palette(as_cmap=True, rot=-.3, light=1)
            sn.clustermap(X_scaled, cmap=cmap, linewidths=.5)
            plt.show()
            break


    def launch_k_means_evaluation(self, starting_gen, max_cluster):
        exp_list_1 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_1, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen)

        exp_list_2 = self.get_pop_phe_lists_of_an_experiment(self.starting_path_2, sample_size=self.to_fetch_sample,
                                                             starting_gen=starting_gen)
        # print("exp list 2: ", exp_list_2)

        ks_dict_1 = self.evaluate_k_means_inter_ind(exp_list_1, max_cluster)
        ks_dict_2 = self.evaluate_k_means_inter_ind(exp_list_2, max_cluster)

        print(ks_dict_1)
        print(ks_dict_2)

        set_idxs_1 = sorted(ks_dict_1.keys())
        set_idxs_2 = sorted(ks_dict_2.keys())

        print(StatisticsToolkit.calculate_statistical_significances(ks_dict_1[set_idxs_1[-1]][:self.sample_size],
                                                                    ks_dict_2[set_idxs_2[-1]][:self.sample_size]))


    def avg_dist_of_each_gen_analyse(self, dists_1, dists_2, starting_gen=0, end_gen=10000, to_save=False, str_key=False):
        new_dists_1 = dists_1
        new_dists_2 = dists_2

        if isinstance(dists_1, str):
            new_dists_1 = fp.open_a_json_as_a_dict(dists_1)
        if isinstance(dists_2, str):
            new_dists_2 = fp.open_a_json_as_a_dict(dists_2)


        list_1 = []
        list_2 = []

        for a_gen in sorted(list([int(x) for x in sorted(new_dists_2.keys())])):
            if str_key:
                list_1.append(np.mean(new_dists_1[str(a_gen)]))
                list_2.append(np.mean(new_dists_2[str(a_gen)]))
            else:
                list_1.append(np.mean(new_dists_1[a_gen]))
                list_2.append(np.mean(new_dists_2[a_gen]))

        print(StatisticsToolkit.calculate_statistical_significances(list_1[starting_gen:end_gen], list_2[starting_gen:end_gen]))

        if to_save:
            cur_time = strftime("-%Y-%m-%d-%H-%M-%S", gmtime())
            # self.plot_dist_gen_curve(new_dists_1, dpi=300,
            #                          save_path='./generated_images/', save_name=('avg_dist_1' + cur_time + '.png'))
            # self.plot_dist_gen_curve(new_dists_2, dpi=300,
            #                          save_path='./generated_images/', save_name=('avg_dist_2' + cur_time + '.png'))
            self.plot_dist_gen_curve([new_dists_1, new_dists_2], dpi=300,
                                     save_path='./generated_images/', save_name=('avg_dist_1_2' + cur_time + '.png'), start_gen=starting_gen, end_gen=end_gen)


matrix_similarity_analyzer = MatrixSimilarityAnalyzer()
# matrix_similarity_analyzer.statistically_compare_two_inter_file_grn_distances('fit', dist_type='manhattan')

# matrix_similarity_analyzer.launch_inter_ind_dists(starting_gen=1, end_gen=2000, dist_type='manhattan',
#                                                       use_average=True, to_plot=True, the_interval=1)
# matrix_similarity_analyzer.launch_k_means_evaluation(2000, 32)

# matrix_similarity_analyzer.launch_inter_ind_dist_only_one(path_idx=2, starting_gen=1, end_gen=2000, dist_type='manhattan',
#                                                       use_average=True, to_plot=True, the_interval=1)

# matrix_similarity_analyzer.avg_dist_of_each_gen_analyse('generated_images/With-Elite-Fixed-Zhenyue-p00-p001-dict_1-2018-11-07-11-21-45.json',
#                                                         'generated_images/With-Elite-Fixed-Zhenyue-p00-p001-dict_2-2018-11-07-11-21-45.json',
#                                                         starting_gen=600, end_gen=800, to_save=False, str_key=True)

# matrix_similarity_analyzer.avg_dist_of_each_gen_analyse('generated_images/Fixed-Zhenyue-p00-p001-dict_1-2018-10-21-06-51-10.json',
#                                                         'generated_images/Fixed-Zhenyue-p00-p001-dict_2-2018-10-21-06-51-10.json',
#                                                         starting_gen=600, end_gen=800, to_save=False, str_key=True)

# matrix_similarity_analyzer.avg_dist_of_each_gen_analyse('generated_images/Fixed-Zhenyue-p00-p001-dict_1-2018-10-21-06-51-10.json',
#                                                         'generated_images/zenyue_complete_sampling_p01_dict_2-2019-02-22-23-10-31.json',
#                                                         starting_gen=500, end_gen=1997, to_save=True, str_key=True)

matrix_similarity_analyzer.avg_dist_of_each_gen_analyse('generated_images/esw_p0_p_01_dict_1-2019-02-22-22-06-56.json',
                                                        'generated_images/esw_p0_p_01_dict_2-2019-02-22-22-06-56.json',
                                                        starting_gen=500, end_gen=2000, to_save=True, str_key=True)

