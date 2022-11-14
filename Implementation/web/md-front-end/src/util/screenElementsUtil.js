const ScreenElementsUtil = {
    resetNavbarItems(itemToCompare) {
        Array.from(document.getElementsByClassName("nav-item dropdown")).forEach((item) => {
            if (itemToCompare && item.isEqualNode(itemToCompare)) {
                return;
            }
            item.classList.remove('show');
            const itemDropdownMenu = item.querySelector(".dropdown-menu");
            if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                itemDropdownMenu.classList.remove('show');
            }
        });
    },
    resetCollapseNavbarItems() {
        const toggleElement = document.querySelector('.navbar-toggler');
        if (toggleElement && toggleElement instanceof HTMLElement) {
            toggleElement.addEventListener('click', () => {
                if (toggleElement && toggleElement instanceof HTMLElement) {
                    toggleElement.classList.toggle('collapsed');
                    const toggleNavContainer = toggleElement.parentElement.querySelector('.navbar-collapse.collapse');
                    if (toggleNavContainer && toggleNavContainer instanceof HTMLElement) {
                        toggleNavContainer.classList.toggle('show');
                    }
                }
            });
        }
    },
    setNavbarMobileMode() {
        ScreenElementsUtil.resetCollapseNavbarItems();

        const container = document.body;
        const outsideNavContainer = container.querySelector('#authContainerOutside');
        const insideNavContainer = container.querySelector('#authContainerInside');
        if (outsideNavContainer && outsideNavContainer instanceof HTMLElement
            && insideNavContainer && insideNavContainer instanceof HTMLElement
            && container && container instanceof HTMLElement) {
            ['resize', 'load', 'unload', 'beforeprint'].forEach(event => {
                window.addEventListener(event, () => {
                    if (container.clientWidth < 992) {
                        outsideNavContainer.hidden = true;
                        insideNavContainer.hidden = false;
                    } else {
                        outsideNavContainer.hidden = false;
                        insideNavContainer.hidden = true;
                    }
                });
            })
        }
    },
    toggleNavbarItems() {
        document.addEventListener('click', (e) => {
            const clickedElement = e.target;
            if (clickedElement && clickedElement instanceof HTMLElement
                && !clickedElement.classList.contains('dropdown-toggle')) {
                this.resetNavbarItems();
            }
        });
        Array.from(document.getElementsByClassName("nav-item dropdown")).forEach((item) => {
            item.addEventListener('click', () => {
                this.resetNavbarItems(item);
                item.classList.toggle('show');
                const itemDropdownMenu = item.querySelector(".dropdown-menu");
                if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                    itemDropdownMenu.classList.toggle('show');
                }
            });
        });
        this.setNavbarMobileMode();
    },
    toggleAccordionItems(e) {
        const clickedElement = e.target;
        if (clickedElement && clickedElement instanceof HTMLElement) {
            clickedElement.classList.toggle('collapsed');
            const accordionItemContainer = clickedElement.parentElement.parentElement.querySelector(".accordion-collapse.collapse");
            if (accordionItemContainer && accordionItemContainer instanceof HTMLElement) {
                accordionItemContainer.classList.toggle('show');
            }
        }
    },
    isClickableTableRow(e, id) {
        const element = e.target;
        if (element instanceof HTMLElement) {
            const cellElement = element.parentElement;
            if (cellElement && cellElement instanceof HTMLElement) {
                if (id && !cellElement.classList.contains('table-cell-clickable')) {
                    return true;
                }
            }
        }
        return false;
    }
}

export default ScreenElementsUtil;