import styled from "styled-components";

export const DashboardsWrapper = styled.div`

    display: flex;
    flex-direction: column;
    gap: 1rem;
    transition: all .3s;

    &.graphs {
        align-items: center;
    }

    @media (max-width: 880px) {
        justify-content: space-evenly;
    }

`;

export const DashboardRow = styled.div`
    display: flex;
    flex-wrap: wrap;
    gap: 1rem;
    width: 100%;
    
    & > div {
        min-width: 17rem;
        flex: 1;
    }
`;

export const DashboardWrapper = styled.div`
    & > div {
        box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.10);
        border-radius: 12px;
        overflow: hidden;
    }
`;
