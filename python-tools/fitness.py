import random
import numpy as np
import math


def get_perturbed_gap(targets, gap_indpb):
    targets_cp = targets.copy()
    for a_GAP in targets_cp:
        for a_gene_idx in range(len(a_GAP)):
            if random.random() < gap_indpb:
                if a_GAP[a_gene_idx] == 1:
                    a_GAP[a_gene_idx] = -1
                elif a_GAP[a_gene_idx] == -1:
                    a_GAP[a_gene_idx] = 1
    return targets_cp


def get_perturbed_gaps(targets, gap_indpb, perturbations):
    perturbed_gaps = []
    for p in range(perturbations):
        perturbed_gaps.append(get_perturbed_gap(targets, gap_indpb))
    return perturbed_gaps


def update_gap(perturbed_t, grn):
    new_perturbed_t = np.dot(perturbed_t, grn)
    new_perturbed_t[new_perturbed_t > 0] = 1
    new_perturbed_t[new_perturbed_t <= 0] = -1
    return new_perturbed_t


def evaluate_a_grn_target(grn, a_target, perturbations, gap_indpb, max_ite):
    targets = np.array([a_target])
    perturbed_ts = get_perturbed_gaps(targets, gap_indpb, perturbations)
    gamma_sum = np.zeros(shape=[1, targets.shape[0]])
    for perturbed_t in perturbed_ts:
        last_perturbed_t = perturbed_t

        is_cycle = False
        for ite in range(max_ite):
            new_perturbed_t = update_gap(last_perturbed_t, grn)

            if np.array_equal(new_perturbed_t, last_perturbed_t):
                is_cycle = True
                break

            last_perturbed_t = new_perturbed_t

        if is_cycle:
            equality = last_perturbed_t == targets
            gamma_before_pow = np.sum(equality, axis=1) / targets.shape[1]
            gamma_after_pow = math.pow(gamma_before_pow, 5)
            gamma_sum += gamma_after_pow
        else:
            gamma_sum += 0
    g = np.sum(gamma_sum) / (perturbations * len(targets))
    fitness_sum = 1 - (math.e ** (-3 * g))
    return fitness_sum


def evaluate_grn(grn, targets, perturbations, gap_indpb=0.15, max_ite=30):
    fit_sum = 0
    for a_target in targets:
        fit_sum += evaluate_a_grn_target(grn, a_target, perturbations, gap_indpb, max_ite)
    return fit_sum / len(targets)

