package br.edu.ifpb.pweb2.estagiotrack.model;

import lombok.Data;

@Data
public class Paginador {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalItems;

    public Paginador(int currentPage, int pageSize, int totalItems) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;

        if (pageSize > 0) {
            this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
        } else {
            this.totalPages = 0;
        }
    }
}
