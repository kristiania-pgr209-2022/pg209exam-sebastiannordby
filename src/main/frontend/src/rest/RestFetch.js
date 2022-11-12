export async function restFetch(spec) {
    if(spec) {
        const result = await fetch(spec);
        const jsonResult = await result.json();

        return jsonResult;
    }

    return null;
}