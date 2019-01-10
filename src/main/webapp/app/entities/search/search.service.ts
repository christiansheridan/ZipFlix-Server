import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISearch } from 'app/shared/model/search.model';

type EntityResponseType = HttpResponse<ISearch>;
type EntityArrayResponseType = HttpResponse<ISearch[]>;

@Injectable({ providedIn: 'root' })
export class SearchService {
    public resourceUrl = SERVER_API_URL + 'api/searches';

    constructor(private http: HttpClient) {}

    create(search: ISearch): Observable<EntityResponseType> {
        return this.http.post<ISearch>(this.resourceUrl, search, { observe: 'response' });
    }

    update(search: ISearch): Observable<EntityResponseType> {
        return this.http.put<ISearch>(this.resourceUrl, search, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<ISearch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISearch[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
