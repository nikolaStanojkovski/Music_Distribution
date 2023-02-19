import {
    ACCORDION_COLLAPSE,
    AUTH_CONTAINER_INSIDE,
    AUTH_CONTAINER_OUTSIDE,
    CLICK,
    COLLAPSE,
    COLLAPSED,
    DATA_ID,
    DRAW_EVENTS,
    DROPDOWN,
    DROPDOWN_MENU,
    DROPDOWN_TOGGLE,
    NAV_ITEM,
    NAVBAR_COLLAPSE,
    NAVBAR_TOGGLER,
    PLAY_BUTTON,
    PLAY_PAUSE_BUTTON,
    SHOW,
    STOP_BUTTON,
    TABLE_CELL_CLICKABLE
} from "../constants/screen";

const ScreenElementsUtil = {
    resetNavbarItems(itemToCompare) {
        Array.from(document.getElementsByClassName(`${NAV_ITEM} ${DROPDOWN}`)).forEach((item) => {
            if (itemToCompare && item.isEqualNode(itemToCompare)) {
                return;
            }
            item.classList.remove(SHOW);
            const itemDropdownMenu = item.querySelector(`.${DROPDOWN_MENU}`);
            if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                itemDropdownMenu.classList.remove(SHOW);
            }
        });
    },
    resetCollapseNavbarItems() {
        const toggleElement = document.querySelector(`.${NAVBAR_TOGGLER}`);
        if (toggleElement && toggleElement instanceof HTMLElement) {
            toggleElement.addEventListener(CLICK, () => {
                if (toggleElement && toggleElement instanceof HTMLElement) {
                    toggleElement.classList.toggle(COLLAPSED);
                    const toggleNavContainer = toggleElement.parentElement.querySelector(`.${NAVBAR_COLLAPSE}.${COLLAPSE}`);
                    if (toggleNavContainer && toggleNavContainer instanceof HTMLElement) {
                        toggleNavContainer.classList.toggle(SHOW);
                    }
                }
            });
        }
    },
    setNavbarMobileMode() {
        ScreenElementsUtil.resetCollapseNavbarItems();

        const container = document.body;
        const outsideNavContainer = container.querySelector(`#${AUTH_CONTAINER_OUTSIDE}`);
        const insideNavContainer = container.querySelector(`#${AUTH_CONTAINER_INSIDE}`);
        if (outsideNavContainer && outsideNavContainer instanceof HTMLElement
            && insideNavContainer && insideNavContainer instanceof HTMLElement
            && container && container instanceof HTMLElement) {
            DRAW_EVENTS.forEach(event => {
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
        document.addEventListener(CLICK, (e) => {
            const clickedElement = e.target;
            if (clickedElement && clickedElement instanceof HTMLElement
                && !clickedElement.classList.contains(DROPDOWN_TOGGLE)) {
                this.resetNavbarItems();
            }
        });
        Array.from(document.getElementsByClassName(`${NAV_ITEM} ${DROPDOWN}`)).forEach((item) => {
            item.addEventListener(CLICK, () => {
                this.resetNavbarItems(item);
                item.classList.toggle(SHOW);
                const itemDropdownMenu = item.querySelector(`.${DROPDOWN_MENU}`);
                if (itemDropdownMenu && itemDropdownMenu instanceof HTMLElement) {
                    itemDropdownMenu.classList.toggle(SHOW);
                }
            });
        });
        this.setNavbarMobileMode();
    },
    toggleAccordionItems(e) {
        const clickedElement = e.target;
        if (clickedElement && clickedElement instanceof HTMLElement) {
            clickedElement.classList.toggle(COLLAPSED);

            const accordionItemContainer = clickedElement
                .parentElement.parentElement
                .querySelector(`.${ACCORDION_COLLAPSE}.${COLLAPSE}`);
            if (accordionItemContainer && accordionItemContainer instanceof HTMLElement) {
                accordionItemContainer.classList.toggle(SHOW);
            }
        }
    },
    isClickableTableRow(e, id) {
        const element = e.target;
        if (element instanceof HTMLElement) {
            const cellElement = element.parentElement;
            if (cellElement && cellElement instanceof HTMLElement) {
                if (id && !cellElement.classList.contains(TABLE_CELL_CLICKABLE)) {
                    return true;
                }
            }
        }
        return false;
    },
    toggleElementClass(element, elementClass) {
        if (element && element instanceof HTMLElement && elementClass) {
            element.classList.toggle(elementClass);
        }
    },
    getTableRowId(element) {
        if (element && element instanceof HTMLElement) {
            const buttonCellWrapper = element.parentElement;
            if (buttonCellWrapper && buttonCellWrapper instanceof HTMLElement) {
                const tableRow = buttonCellWrapper.parentElement;
                if (tableRow && tableRow instanceof HTMLElement
                    && tableRow.hasAttributes()) {
                    return tableRow.getAttribute(DATA_ID);
                }
            }
        }
        return undefined;
    },
    toggleSongPlayButtonClassList(button) {
        ScreenElementsUtil.toggleElementClass(button, PLAY_BUTTON);
        ScreenElementsUtil.toggleElementClass(button, STOP_BUTTON);
    },
    toggleSongPlayButton(button) {
        if (button && button instanceof HTMLElement) {
            this.toggleSongPlayButtonClassList(button);
            const songId = button.parentElement.parentElement.getAttribute(DATA_ID);
            Array.from(document.querySelectorAll(`.${PLAY_PAUSE_BUTTON}`)).forEach((btn) => {
                const itemSongId = btn.parentElement.parentElement.getAttribute(DATA_ID);
                if (itemSongId !== songId && btn.classList.contains(STOP_BUTTON)) {
                    this.toggleSongPlayButtonClassList(btn);
                }
            });
        }
    },
    reloadDomain() {
        const baseUrl = window.location.origin;
        window.location.replace(baseUrl);
    }
}

export default ScreenElementsUtil;