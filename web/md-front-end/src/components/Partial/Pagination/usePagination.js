import React from "react";
import {EMPTY_STRING} from "../../../constants/alphabet";

const usePagination = (props) => {

    const pageNumber = props.pageNumber;
    const totalPages = props.totalPages;
    const changePage = props.changePage;
    const [items, updateItems] = React.useState([]);
    const [activePage, updateActivePage] = React.useState(0);

    React.useEffect(() => {
        const renderListItem = (index) => {
            const isCurrentPage = pageNumber === (index - 1);
            if (isCurrentPage) {
                updateActivePage(index - 1);
            }

            return (
                <li key={index} className={`page-item ${isCurrentPage ? 'active' : EMPTY_STRING}`}>
                    <button className="page-link" onClick={() => (!isCurrentPage)
                        ? changePage(index - 1) : EMPTY_STRING}>{index}</button>
                </li>
            );
        };
        const renderPageBeginnings = () => {
            let startPage = pageNumber - 2;
            let endPage = pageNumber + 2;

            if (startPage <= 0) {
                endPage -= (startPage - 1);
                startPage = 1;
            }

            if (endPage > totalPages)
                endPage = totalPages;

            return {start: startPage, end: endPage};
        };
        const renderListItems = () => {
            const items = [];

            const page = renderPageBeginnings();
            for (let i = page.start; i <= page.end; ++i) {
                items.push(renderListItem(i));
            }
            return items;
        };

        updateItems(renderListItems());
    }, [pageNumber, totalPages, changePage]);


    return {activePage, items, totalPages, changePage};
}

export default usePagination;