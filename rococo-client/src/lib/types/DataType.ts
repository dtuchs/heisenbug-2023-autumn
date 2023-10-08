export type StoreDataType<Type> = {
    data: Type[],
    noMoreData: boolean,
    isLoading: boolean,
    ignoreIds: string[],
}