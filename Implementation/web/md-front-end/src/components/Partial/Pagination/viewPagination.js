import React from "react";

const viewPagination = (props) => {
    return (
        <nav className={"my-4"}>
            <ul className="pagination justify-content-center">
                <li className="page-item">
                    <button className="page-link"
                            onClick={() => ((props.activePage - 1) >= 0)
                                ? props.changePage(props.activePage - 1) : ''}>
                        Previous
                    </button>
                </li>
                {props.items}
                <li className="page-item">
                    <button className="page-link"
                            onClick={() => ((props.activePage + 1) < (props.totalPages))
                                ? props.changePage(props.activePage + 1) : ''}>
                        Next
                    </button>
                </li>
            </ul>
        </nav>
    );
}

export default viewPagination;