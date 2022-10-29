import React from 'react';

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
    setNavbarMobileMode() {
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

        const container = document.body;
        const outsideNavContainer = container.querySelector('#authContainerOutside');
        const insideNavContainer = container.querySelector('#authContainerInside');
        if (outsideNavContainer && outsideNavContainer instanceof HTMLElement
            && insideNavContainer && insideNavContainer instanceof HTMLElement
            && container && container instanceof HTMLElement) {
            ['resize', 'load'].forEach(event => {
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
    }
}

export default ScreenElementsUtil;