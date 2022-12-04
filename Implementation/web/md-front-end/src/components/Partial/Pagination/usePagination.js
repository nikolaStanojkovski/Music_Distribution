import React from "react";

const usePagination = (props) => {

    const totalPages = props.totalPages;
    const changePage = props.changePage;
    const [items, updateItems] = React.useState([]);
    const [activePage, updateActivePage] = React.useState(0);

    React.useEffect(() => {
        updateItems(renderListItems());
    }, [props.pageNumber, props.totalPages, props.changePage]);

    const renderListItem = (index) => {
        const isCurrentPage = props.pageNumber === (index - 1);
        if (isCurrentPage) {
            updateActivePage(index - 1);
        }

        return (
            <li key={index} className={`page-item ${isCurrentPage ? 'active' : ''}`}>
                <button className="page-link" onClick={() => (!isCurrentPage)
                    ? props.changePage(index - 1) : ''}>{index}</button>
            </li>
        );
    }

    const renderPageBeginnings = () => {
        let startPage = props.pageNumber - 2;
        let endPage = props.pageNumber + 2;

        if (startPage <= 0) {
            endPage -= (startPage - 1);
            startPage = 1;
        }

        if (endPage > props.totalPages)
            endPage = props.totalPages;

        return {start: startPage, end: endPage};
    }

    const renderListItems = () => {
        const items = [];

        const page = renderPageBeginnings();
        for (let i = page.start; i <= page.end; ++i) {
            items.push(renderListItem(i));
        }
        return items;
    };

    return {activePage, items, totalPages, changePage};
}

export default usePagination;