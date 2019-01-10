/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZipVidzTestModule } from '../../../test.module';
import { ZipFlixUserDetailComponent } from 'app/entities/zip-flix-user/zip-flix-user-detail.component';
import { ZipFlixUser } from 'app/shared/model/zip-flix-user.model';

describe('Component Tests', () => {
    describe('ZipFlixUser Management Detail Component', () => {
        let comp: ZipFlixUserDetailComponent;
        let fixture: ComponentFixture<ZipFlixUserDetailComponent>;
        const route = ({ data: of({ zipFlixUser: new ZipFlixUser('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [ZipFlixUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ZipFlixUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ZipFlixUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.zipFlixUser).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
