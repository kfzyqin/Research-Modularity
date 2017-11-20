import os
import matplotlib.pyplot as plt


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
            if os.path.isdir(os.path.join(a_dir, name))]


def save_a_list_graph(a_list, y_label, path, file_name):
    plt.plot(a_list)
    plt.ylabel(y_label)
    plt.savefig(path + os.sep + file_name)
    plt.close()


def save_multiple_lists_graph(lists, labels, path, file_name):
    for idx in range(len(lists)):
        plt.plot(lists[idx], label=labels[idx])
    plt.savefig(path + os.sep + file_name)
    plt.legend(labels)
    plt.close()
