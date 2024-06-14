import * as React from "react";
import {useCallback, useEffect, useState} from "react";
import './CustomMenu.css'


export function CustomMenuContainer({children, contextMenu}) {
    const [menu, setMenu] = useState(null);

    const onNodeContextMenu = useCallback(
        (event) => {
            // Prevent native context menu from showing
            event.preventDefault();
            // Calculate position of the context menu. We want to make sure it
            // doesn't get positioned off-screen.
            setMenu({
                left: event.clientX,
                top: event.clientY,
            });
        },
        [setMenu],
    );

    useEffect(() => {
        const handleClick = () => setMenu(null);
        window.addEventListener("click", handleClick);
        return () => {
            window.removeEventListener("click", handleClick);
        };
    }, [setMenu]);

    return (
        <div
            onContextMenu={onNodeContextMenu}
        >
            {children}
            {
                menu
                    ? <div
                        style={{
                            top: menu.top,
                            left: menu.left
                        }}
                        className="context-menu"
                    >
                        {contextMenu}
                    </div>
                    : null
            }
        </div>
    );
}