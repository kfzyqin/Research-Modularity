import os
import file_processor as fp
from sklearn.cluster import KMeans
import numpy as np
from sklearn.metrics import silhouette_samples, silhouette_score

prefix_path = os.path.expanduser("~")
starting_path_1 = prefix_path + '/Portal/generated-outputs/balanced-combinations-p02'

final_phenotypes = fp.get_last_grn_phenotypes(100, 'fit', starting_path_1)
np_phenotypes = np.array(final_phenotypes)


def generate_a_tsv_file():
    saving_name = starting_path_1.replace((prefix_path + '/Portal/generated-outputs/'), '')

    f1=open('./miscellaneous/' + saving_name + '.tsv', 'w+')

    for a_final_phenotype in final_phenotypes:
        print(a_final_phenotype)
        to_write = '\t'.join(map(str, a_final_phenotype))
        f1.write(to_write + '\n')

    f1.close()


def k_means_analysis(n_clusters):
    for a_cluster in range(3, n_clusters):
        kmeans = KMeans(n_clusters=a_cluster, random_state=2)
        cluster_labels = kmeans.fit_predict(np_phenotypes)
        silhouette_avg = silhouette_score(np_phenotypes, cluster_labels)
        print("For n_clusters = ", a_cluster,
              "The average silhouette_score is :", silhouette_avg)


k_means_analysis(10)
