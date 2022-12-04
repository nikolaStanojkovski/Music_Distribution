import React from 'react';
import usePagination from "./usePagination";
import viewPagination from "./viewPagination";

const Pagination = (props) => {

    const {activePage, items, totalPages, changePage} = usePagination(props);

    return viewPagination({activePage, items, totalPages, changePage});
}

export default Pagination;