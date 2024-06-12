import styled, {css} from "styled-components";

export const ContextMenu = styled.div`
    position: absolute;
    width: 160px;
    background-color: #424242;
    border-radius: 5px;
    box-sizing: border-box;

    ${({top, left}) => css`
        top: ${top}px;
        left: ${left}px;
    `}
`;