import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';

type EntityResponseType = HttpResponse<IRecommendedVideos>;
type EntityArrayResponseType = HttpResponse<IRecommendedVideos[]>;

@Injectable({ providedIn: 'root' })
export class RecommendedVideosService {
    public resourceUrl = SERVER_API_URL + 'api/recommended-videos';

    constructor(private http: HttpClient) {}

    create(recommendedVideos: IRecommendedVideos): Observable<EntityResponseType> {
        return this.http.post<IRecommendedVideos>(this.resourceUrl, recommendedVideos, { observe: 'response' });
    }

    update(recommendedVideos: IRecommendedVideos): Observable<EntityResponseType> {
        return this.http.put<IRecommendedVideos>(this.resourceUrl, recommendedVideos, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IRecommendedVideos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecommendedVideos[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
