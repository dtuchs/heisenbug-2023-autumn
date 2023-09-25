export type Pageable<ContentType> = {
    content: ContentType,
    pageable: {
        pageNumber: number,
        pageSize: number,
        sort: {
            empty: boolean,
            sorted: boolean,
            unsorted: boolean,
        },
        offset: number,
        paged: boolean,
        unpaged: boolean,
    },
    totalPages: number,
    totalElements: number,
    last: boolean,
    size: number,
    number: number,
    sort: {
        empty: boolean,
        sorted: boolean,
        unsorted: boolean,
    },
    numberOfElements: number,
    first: boolean,
    empty: boolean,
}