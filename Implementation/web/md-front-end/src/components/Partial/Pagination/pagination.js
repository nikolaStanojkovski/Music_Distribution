import React from 'react';

const Pagination = (props) => {
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

    return (
        <nav className={"my-4"}>
            <ul className="pagination justify-content-center">
                <li className="page-item">
                    <button className="page-link"
                            onClick={() => ((activePage - 1) >= 0)
                                ? props.changePage(activePage - 1) : ''}>
                        Previous
                    </button>
                </li>
                {items}
                <li className="page-item">
                    <button className="page-link"
                            onClick={() => ((activePage + 1) < (props.totalPages))
                                ? props.changePage(activePage + 1) : ''}>
                        Next
                    </button>
                </li>
            </ul>
        </nav>
    );
}

export default Pagination;