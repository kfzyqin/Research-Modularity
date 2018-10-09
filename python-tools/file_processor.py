import os
import matplotlib.pyplot as plt
import operator as op
import math
import ast
import json


def ncr(n, r):
    r = min(r, n-r)
    if r == 0: return 1
    numer = reduce(op.mul, xrange(n, n-r, -1))
    denom = reduce(op.mul, xrange(1, r+1))
    return numer//denom


def get_immediate_subdirectories(a_dir, no_limitation=0):
    if no_limitation == 0:
        return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
                if os.path.isdir(os.path.join(a_dir, name))]
    else:
        return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
                if os.path.isdir(os.path.join(a_dir, name)) and len(os.listdir((a_dir + os.sep + name))) >= no_limitation]


def save_a_list_graph(a_list, y_label, path, file_name, vertical_lines=None):
    if path[-1] != os.sep:
        path += os.sep
    plt.plot(a_list, linewidth=1.0)
    plt.ylabel(y_label)
    if vertical_lines:
        for xc in vertical_lines:
            plt.axvline(x=xc, color='green', linestyle='--')
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def plot_a_list_graph(a_list, y_label, dpi=500, save_path=None, save_name=None, marker=None):
    plt.clf()
    xs = range(len(a_list))
    plt.plot(xs, a_list, marker=marker)
    plt.ylabel(y_label)
    if save_path is None:
        plt.show()
    else:
        plt.savefig(save_path + os.sep + save_name, dpi=dpi)


def save_multiple_lists_graph(lists, labels, path, file_name, vertical_lines=None):
    if vertical_lines is None:
        vertical_lines = []
    for idx in range(len(lists)):
        plt.plot( lists[idx], label=labels[idx])
    if len(vertical_lines) != 2:
        for xc in vertical_lines:
            plt.axvline(x=xc)
    else:
        plt.axvline(x=vertical_lines[0], color='green', linestyle='--')
        plt.axvline(x=vertical_lines[1], color='red', linestyle='--')

    plt.legend(fontsize=8, loc=8)
    plt.savefig(path + os.sep + file_name, dpi=200)
    plt.close()


def count_number_of_edges(a_grn):
    return sum(x != 0 for x in a_grn)


def calculate_binomial_distribution(n, p, to_plot=False):
    prob_list = []
    for i in range(n+1):
        combination = ncr(n, i)
        a_result = combination * math.pow(p, i) * math.pow((1-p), (n-i))
        prob_list.append(a_result)
        print "When k is ", i, "\t the probability is ", a_result
    if to_plot:
        plot_a_list_graph(prob_list, 'Probability')


def write_a_list_into_a_file(a_list, file_path, file_name):
    the_file = open(file_path + os.sep + file_name, 'w')
    for item in a_list:
        the_file.write("%s " % item)


def save_lists_graph(lists, labels=None, ver_lines=None, path="", file_name="", marker=None, colors=None, dpi=500):
    fig, ax0 = plt.subplots(nrows=1, figsize=(16, 10))
    default_colors = ['b', 'g', 'r', 'c', 'm', 'y', 'k', 'w']
    for a_list_idx in range(len(lists)):
        if not (labels is None):
            ax0.scatter(list(range(len(lists[a_list_idx]))), lists[a_list_idx], label=labels[a_list_idx], marker=marker,
                     c=default_colors[int(colors[a_list_idx])] if colors is not None else default_colors[a_list_idx%len(default_colors)])
        else:
            ax0.scatter(list(range(len(lists[a_list_idx]))), lists[a_list_idx], marker=marker,
                     c=default_colors[int(colors[a_list_idx])] if colors is not None else default_colors[a_list_idx%len(default_colors)])
        ax0.legend()

    if ver_lines is not None:
        for v_l in ver_lines:
            plt.axvline(x=v_l, ls='dashed', c='y')

    if path and file_name:
        plt.savefig(path + os.sep + file_name, dpi=dpi)
    else:
        plt.show()
    plt.clf()
    plt.close()


def read_a_file_line_by_line(a_file, convert_list=False):
    rtn = []
    with open(a_file) as f:
        content = f.readlines()
        if convert_list:
            for a_content in content:
                x = ast.literal_eval(a_content)
                rtn.append(x)
            return rtn
        else:
            return content


def get_last_grn_phenotypes(sample_size, a_type, root_directory_path):
    suffix = ""
    if a_type == 'fit':
        suffix += '_fit.list'
    elif a_type == 'mod':
        suffix += '_mod.list'
    else:
        raise RuntimeError("GRN phenotypes are unexpected")

    file_target = 'phenotypes' + suffix

    phenotypes = []
    txt_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(file_target):
                txt_files.append(root + os.sep + a_file)

    txt_files = txt_files[:sample_size]

    for a_txt_file in txt_files:
        phenotypes.append(ast.literal_eval(read_a_file_line_by_line(a_txt_file)[-1]))

    return phenotypes


def open_a_json_as_a_dict(file_path):
    with open(file_path) as f:
        data = json.load(f)
    return data
